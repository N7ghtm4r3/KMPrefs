package com.tecknobit.kmprefs

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

@Serializable
private data class CollectionRecord(
    val identifier: Int,
    val label: String
)

class KMPrefsCollectionBehaviorTests {

    @Test
    fun addingToAMissingCollectionCreatesAndPersistsIt() {
        withCleanPreferences(
            feature = "collection-create"
        ) { preferences ->
            preferences.addToCollection(
                element = "first",
                key = "items"
            )

            assertTrue(
                actual = preferences.hasKey("items"),
                message = "Adding to a missing collection must create its key."
            )
            assertEquals(
                expected = listOf("first"),
                actual = preferences.retrieve<List<String>>("items"),
                message = "The newly created collection must contain the added element."
            )
        }
    }

    @Test
    fun addingMultipleElementsPreservesOrderAndDuplicates() {
        withCleanPreferences(
            feature = "collection-add-all"
        ) { preferences ->
            preferences.store(
                key = "items",
                value = listOf("existing")
            )

            preferences.addAllToCollection(
                elements = listOf("next", "next", "last"),
                key = "items"
            )

            assertEquals(
                expected = listOf("existing", "next", "next", "last"),
                actual = preferences.retrieve<List<String>>("items"),
                message = "Bulk addition must append elements without deduplicating them."
            )
        }
    }

    @Test
    fun removingOneElementRemovesItsFirstOccurrence() {
        withCleanPreferences(
            feature = "collection-remove-one"
        ) { preferences ->
            preferences.store(
                key = "items",
                value = listOf("repeat", "middle", "repeat")
            )

            preferences.removeFromCollection(
                element = "repeat",
                key = "items"
            )

            assertEquals(
                expected = listOf("middle", "repeat"),
                actual = preferences.retrieve<List<String>>("items"),
                message = "Single removal must remove only the first matching occurrence."
            )
        }
    }

    @Test
    fun removingMultipleValuesRemovesEveryMatchingOccurrence() {
        withCleanPreferences(
            feature = "collection-remove-all"
        ) { preferences ->
            preferences.store(
                key = "items",
                value = listOf("keep", "remove", "remove", "also-keep", "other")
            )

            preferences.removeAllFromCollection(
                elements = listOf("remove", "other"),
                key = "items"
            )

            assertEquals(
                expected = listOf("keep", "also-keep"),
                actual = preferences.retrieve<List<String>>("items"),
                message = "Bulk removal must remove all occurrences selected by value."
            )
        }
    }

    @Test
    fun upsertAppendsAnElementWhenItsSelectorIsAbsent() {
        withCleanPreferences(
            feature = "collection-upsert-append"
        ) { preferences ->
            val original = CollectionRecord(
                identifier = 1,
                label = "original"
            )
            val appended = CollectionRecord(
                identifier = 2,
                label = "appended"
            )
            preferences.store(
                key = "records",
                value = listOf(original)
            )

            preferences.upsertToCollection(
                element = appended,
                elementSelector = CollectionRecord::identifier,
                key = "records"
            )

            assertEquals(
                expected = listOf(original, appended),
                actual = preferences.retrieve<List<CollectionRecord>>("records"),
                message = "An absent selector must append the new record."
            )
        }
    }

    @Test
    fun upsertReplacesOnlyTheFirstMatchingElementInPlace() {
        withCleanPreferences(
            feature = "collection-upsert-replace"
        ) { preferences ->
            val firstMatch = CollectionRecord(
                identifier = 1,
                label = "first"
            )
            val retained = CollectionRecord(
                identifier = 2,
                label = "retained"
            )
            val laterMatch = CollectionRecord(
                identifier = 1,
                label = "later"
            )
            val replacement = CollectionRecord(
                identifier = 1,
                label = "replacement"
            )
            preferences.store(
                key = "records",
                value = listOf(firstMatch, retained, laterMatch)
            )

            preferences.upsertToCollection(
                element = replacement,
                elementSelector = CollectionRecord::identifier,
                key = "records"
            )

            assertEquals(
                expected = listOf(replacement, retained, laterMatch),
                actual = preferences.retrieve<List<CollectionRecord>>("records"),
                message = "Upsert must replace the first selector match without reordering the collection."
            )
        }
    }

    @Test
    fun customMutableCollectionUsageIsPersisted() {
        withCleanPreferences(
            feature = "collection-custom-usage"
        ) { preferences ->
            preferences.store(
                key = "numbers",
                value = listOf(3, 1, 2)
            )

            preferences.useMutableCollection<Int>(
                key = "numbers"
            ) { collection ->
                collection.add(4)
                val sorted = collection.sorted()
                collection.clear()
                collection.addAll(sorted)
            }

            assertEquals(
                expected = listOf(1, 2, 3, 4),
                actual = preferences.retrieve<List<Int>>("numbers"),
                message = "Mutations performed by custom collection usage must be stored."
            )
        }
    }

    @Test
    fun collectionMutationFailureLeavesAnExistingValueUnchanged() {
        withCleanPreferences(
            feature = "collection-usage-failure"
        ) { preferences ->
            preferences.store(
                key = "items",
                value = listOf("stable")
            )

            assertFailsWith<IllegalStateException>(
                message = "The exception raised by custom collection usage must propagate."
            ) {
                preferences.useMutableCollection<String>(
                    key = "items"
                ) { collection ->
                    collection.add("transient")
                    throw IllegalStateException("stop")
                }
            }

            assertEquals(
                expected = listOf("stable"),
                actual = preferences.retrieve<List<String>>("items"),
                message = "A failed custom mutation must not persist its temporary changes."
            )
        }
    }

    @Test
    fun nonCollectionValueRejectsMutationWithoutChangingData() {
        withCleanPreferences(
            feature = "collection-invalid-value"
        ) { preferences ->
            preferences.store(
                key = "items",
                value = 42
            )

            assertFailsWith<SerializationException>(
                message = "A scalar value must not be accepted as a serialized collection."
            ) {
                preferences.addToCollection(
                    element = 7,
                    key = "items"
                )
            }

            assertEquals(
                expected = 42,
                actual = preferences.retrieve<Int>("items"),
                message = "A rejected collection mutation must preserve the original scalar."
            )
        }
    }

    @Test
    fun upsertTargetResolutionReturnsTheFirstMatchOrMinusOne() {
        withCleanPreferences(
            feature = "collection-selector"
        ) { preferences ->
            val records = mutableListOf(
                CollectionRecord(
                    identifier = 4,
                    label = "first"
                ),
                CollectionRecord(
                    identifier = 8,
                    label = "middle"
                ),
                CollectionRecord(
                    identifier = 4,
                    label = "later"
                )
            )

            val firstMatch = with(preferences) {
                records.resolveUpsertTargetIndex(
                    elementSelector = CollectionRecord::identifier,
                    selectorTarget = 4
                )
            }
            val missing = with(preferences) {
                records.resolveUpsertTargetIndex(
                    elementSelector = CollectionRecord::identifier,
                    selectorTarget = 99
                )
            }

            assertEquals(
                expected = 0,
                actual = firstMatch,
                message = "Selector resolution must return the first matching index."
            )
            assertEquals(
                expected = -1,
                actual = missing,
                message = "Selector resolution must return minus one when no element matches."
            )
        }
    }

}
