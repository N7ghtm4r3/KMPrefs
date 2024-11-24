package com.tecknobit.kmprefs

import java.util.prefs.Preferences

/**
 * The **KMPrefs** class helps to manage the preferences storing the data locally
 *
 * @param path Is the path where store the data
 *
 * @author N7ghtm4r3 - Tecknobit
 */
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class KMPrefs actual constructor(
    path: String
) {

    /**
     * **sharedPreferences** -> the instance used to manage locally the preferences
     */
    private val preferences = Preferences.userRoot().node(path)

    /**
     * Method to store locally a [String] value
     *
     * @param key Is the key of the string
     * @param value Is the value to store
     */
    actual fun storeString(
        key: String,
        value: String?
    ) {
        if (value == null)
            preferences.remove(key)
        else
            preferences.put(key, value)
    }

    /**
     * Method to fetch locally a [String] value
     *
     * @param key Is the key of the string to fetch
     * @param defValue Is the value to to return if the searched one does not exist
     */
    actual fun fetchString(
        key: String,
        defValue: String?
    ): String? {
        return preferences.get(key, defValue)
    }

    /**
     * Method to remove locally a [String] value by its key
     *
     * @param key Is the key of the string to remove
     */
    actual fun removeString(key: String) {
        preferences.remove(key)
    }

    /**
     * Method to clear the all preferences specified by the path
     */
    actual fun clearAll() {
        preferences.clear()
    }

}