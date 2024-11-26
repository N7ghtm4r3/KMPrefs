package com.tecknobit.kmprefs

import java.util.prefs.Preferences

/**
 * The **PrefsWorker** class helps to manage the preferences storing the data locally using the [Preferences] built-in
 * mechanism
 *
 * @param path Is the path where store the data
 *
 * @author N7ghtm4r3 - Tecknobit
 */
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
internal actual class PrefsWorker actual constructor(
    path: String
) {

    /**
     * **preferences** -> the instance used to manage locally the preferences on `JVM`
     */
    private val preferences = Preferences.userRoot().node(path)

    /**
     * Method to store locally a value
     *
     * @param key Is the key of the value
     * @param value Is the value to store
     */
    actual fun <T> store(
        key: String,
        value: T?,
    ) {
        if(value == null) {
            remove(
                key = key
            )
        } else
            preferences.put(key, value.toString())
    }

    /**
     * Method to fetch locally a  value
     *
     * @param key Is the key of the value to fetch
     * @param defValue Is the value to return if the searched one does not exist
     * @return fetched value as [String]
     */
    actual fun <T> fetch(
        key: String,
        defValue: T?,
    ): String? {
        return preferences.get(key, defValue?.toString())
    }

    /**
     * Method to remove locally a value by its key
     *
     * @param key Is the key of the value to remove
     */
    actual fun remove(
        key: String
    ) {
        preferences.remove(key)
    }

    /**
     * Method to clear the all preferences specified by the path
     */
    actual fun clearAll() {
        preferences.clear()
    }

}