package com.tecknobit.kmprefs

import kotlinx.serialization.Serializable
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

@Serializable
private data class StoredProfile(
    val identifier: Int,
    val displayName: String,
    val roles: List<String>
)

class KMPrefsStorageBehaviorTests {

    @Test
    fun missingKeysReturnTheirDefaultAndRemainAbsent() {
        withCleanPreferences(
            feature = "missing-default"
        ) { preferences ->
            val retrieved = preferences.retrieve(
                key = "missing",
                defValue = "fallback"
            )

            assertEquals(
                expected = "fallback",
                actual = retrieved,
                message = "A missing preference must resolve to the supplied default."
            )
            assertFalse(
                actual = preferences.hasKey("missing"),
                message = "Reading a missing preference must not create it."
            )
        }
    }

    @Test
    fun scalarValuesCompleteASerializationRoundTrip() {
        withCleanPreferences(
            feature = "scalar-round-trip"
        ) { preferences ->
            preferences.store(
                key = "text",
                value = "KMPrefs"
            )
            preferences.store(
                key = "count",
                value = 42
            )
            preferences.store(
                key = "ratio",
                value = 3.5
            )
            preferences.store(
                key = "enabled",
                value = true
            )

            assertEquals(
                expected = "KMPrefs",
                actual = preferences.retrieve<String>("text"),
                message = "String values must survive storage."
            )
            assertEquals(
                expected = 42,
                actual = preferences.retrieve<Int>("count"),
                message = "Integer values must survive storage."
            )
            assertEquals(
                expected = 3.5,
                actual = preferences.retrieve<Double>("ratio"),
                message = "Double values must survive storage."
            )
            assertEquals(
                expected = true,
                actual = preferences.retrieve<Boolean>("enabled"),
                message = "Boolean values must survive storage."
            )
        }
    }

    @Test
    fun serializableModelsPreserveNestedData() {
        withCleanPreferences(
            feature = "model-round-trip"
        ) { preferences ->
            val profile = StoredProfile(
                identifier = 7,
                displayName = "Ada",
                roles = listOf("reader", "editor")
            )

            preferences.store(
                key = "profile",
                value = profile
            )

            assertEquals(
                expected = profile,
                actual = preferences.retrieve<StoredProfile>("profile"),
                message = "Serializable models must retain all fields."
            )
        }
    }

    @Test
    fun storingNullRemovesAnExistingPreference() {
        withCleanPreferences(
            feature = "null-removal"
        ) { preferences ->
            preferences.store(
                key = "temporary",
                value = "value"
            )

            preferences.store<String>(
                key = "temporary",
                value = null
            )

            assertFalse(
                actual = preferences.hasKey("temporary"),
                message = "A null store must remove the existing key."
            )
            assertEquals(
                expected = "removed",
                actual = preferences.retrieve(
                    key = "temporary",
                    defValue = "removed"
                ),
                message = "A removed key must resolve to its default."
            )
        }
    }

    @Test
    fun consumeRetrievalDeliversStoredAndDefaultValuesOnce() {
        withCleanPreferences(
            feature = "consume"
        ) { preferences ->
            preferences.store(
                key = "stored",
                value = 15
            )
            var storedInvocations = 0
            var consumedStored: Int? = null
            var defaultInvocations = 0
            var consumedDefault: Int? = null

            preferences.consumeRetrieval<Int>(
                key = "stored",
                defValue = -1
            ) { value ->
                storedInvocations += 1
                consumedStored = value
            }
            preferences.consumeRetrieval<Int>(
                key = "missing",
                defValue = 99
            ) { value ->
                defaultInvocations += 1
                consumedDefault = value
            }

            assertEquals(
                expected = 1,
                actual = storedInvocations,
                message = "Stored-value consumption must invoke the callback once."
            )
            assertEquals(
                expected = 15,
                actual = consumedStored,
                message = "Stored-value consumption must deserialize the value."
            )
            assertEquals(
                expected = 1,
                actual = defaultInvocations,
                message = "Default-value consumption must invoke the callback once."
            )
            assertEquals(
                expected = 99,
                actual = consumedDefault,
                message = "Missing-value consumption must deliver the supplied default."
            )
        }
    }

    @Test
    fun removeAffectsOnlyItsKeyAndIsIdempotent() {
        withCleanPreferences(
            feature = "remove"
        ) { preferences ->
            preferences.store(
                key = "removed",
                value = "first"
            )
            preferences.store(
                key = "retained",
                value = "second"
            )

            preferences.removeValue("removed")
            preferences.removeValue("removed")

            assertFalse(
                actual = preferences.hasKey("removed"),
                message = "The selected preference must be absent after removal."
            )
            assertEquals(
                expected = "second",
                actual = preferences.retrieve<String>("retained"),
                message = "Removing one preference must not affect another."
            )
        }
    }

    @Test
    fun clearRemovesEveryPreferenceInItsPath() {
        withCleanPreferences(
            feature = "clear"
        ) { preferences ->
            preferences.store(
                key = "first",
                value = 1
            )
            preferences.store(
                key = "second",
                value = 2
            )

            preferences.clearAll()

            assertFalse(
                actual = preferences.hasKey("first"),
                message = "Clear must remove the first stored key."
            )
            assertFalse(
                actual = preferences.hasKey("second"),
                message = "Clear must remove the second stored key."
            )
        }
    }

    @Test
    fun instancesSharingAPathObservePersistedValues() {
        val path = uniquePreferencesPath("shared-path")
        val first = KMPrefs(
            path = path
        )
        try {
            first.clearAll()
            first.store(
                key = "shared",
                value = "persisted"
            )
            val second = KMPrefs(
                path = path
            )

            assertEquals(
                expected = "persisted",
                actual = second.retrieve<String>("shared"),
                message = "A second instance on the same path must observe persisted data."
            )
        } finally {
            first.clearAll()
        }
    }

    @Test
    fun differentPathsFollowThePlatformStorageContract() {
        val first = KMPrefs(
            path = uniquePreferencesPath("isolation-first")
        )
        val second = KMPrefs(
            path = uniquePreferencesPath("isolation-second")
        )
        try {
            first.clearAll()
            second.clearAll()
            first.store(
                key = "same-key",
                value = "first-value"
            )
            second.store(
                key = "same-key",
                value = "second-value"
            )

            if(preferencesPathsAreIsolated) {
                assertEquals(
                    expected = "first-value",
                    actual = first.retrieve<String>("same-key"),
                    message = "Isolated paths must retain their own values."
                )
                assertEquals(
                    expected = "second-value",
                    actual = second.retrieve<String>("same-key"),
                    message = "Isolated paths must retain equal keys independently."
                )
            } else {
                assertEquals(
                    expected = "second-value",
                    actual = first.retrieve<String>("same-key"),
                    message = "Web paths must share equal keys within the same origin."
                )
            }

            first.clearAll()

            assertFalse(
                actual = first.hasKey("same-key"),
                message = "Clear must remove the value visible from the clearing instance."
            )
            if(preferencesPathsAreIsolated) {
                assertEquals(
                    expected = "second-value",
                    actual = second.retrieve<String>("same-key"),
                    message = "Clearing one isolated path must not affect another path."
                )
            } else {
                assertFalse(
                    actual = second.hasKey("same-key"),
                    message = "Clearing Web storage must affect every path in the same origin."
                )
            }
        } finally {
            first.clearAll()
            second.clearAll()
        }
    }

    @Test
    fun publicWorkerStoresRawValuesAndRemovesNulls() {
        val worker = PrefsWorker(
            path = uniquePreferencesPath("worker")
        )
        try {
            worker.clearAll()
            worker.store(
                key = "raw",
                value = 73
            )

            assertEquals(
                expected = "73",
                actual = worker.retrieve<String>(
                    key = "raw",
                    defValue = null
                ),
                message = "The platform worker must expose its persisted string representation."
            )

            worker.store<Int>(
                key = "raw",
                value = null
            )

            assertFalse(
                actual = worker.hasKey("raw"),
                message = "The platform worker must remove a key when storing null."
            )
        } finally {
            worker.clearAll()
        }
    }

}
