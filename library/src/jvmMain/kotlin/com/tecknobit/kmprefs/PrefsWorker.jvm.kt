package com.tecknobit.kmprefs

import java.util.prefs.Preferences

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
internal actual class PrefsWorker actual constructor(
    path: String
) {

    /**
     * **preferences** -> the instance used to manage locally the preferences on `JVM`
     */
    private val preferences = Preferences.userRoot().node(path)

    actual fun store(
        key: String,
        value: Any?,
    ) {
        if(value == null) {
            remove(
                key = key
            )
        } else
            preferences.put(key, value.toString())
    }

    actual fun <T> fetch(
        key: String,
        defValue: T?,
    ): String? {
        return preferences.get(key, defValue?.toString())
    }

    actual fun remove(
        key: String
    ) {
        preferences.remove(key)
    }

    actual fun clearAll() {
        preferences.clear()
    }

}