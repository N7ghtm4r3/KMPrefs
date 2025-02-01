package com.tecknobit.kmprefs

import android.content.Context
import com.tecknobit.equinoxcore.utilities.AppContext

/**
 * The **PrefsWorker** class helps to manage the preferences storing the data locally using the [android.content.SharedPreferences]
 * built-in mechanism
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
     * **sharedPreferences** -> the instance used to manage locally the preferences on `Android`
     */
    private val sharedPreferences = AppContext.get().getSharedPreferences(
        path,
        Context.MODE_PRIVATE
    )

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
        sharedPreferences.edit().putString(key, value?.toString()).apply()
    }

    /**
     * Method to retrieve locally a value
     *
     * @param key Is the key of the value to retrieve
     * @param defValue Is the value to return if the searched one does not exist
     * @return fetched value as [String]
     */
    actual fun <T> retrieve(
        key: String,
        defValue: T?,
    ): String? {
        return sharedPreferences.getString(key, defValue?.toString())
    }

    /**
     * Method to remove locally a value by its key
     *
     * @param key Is the key of the value to remove
     */
    actual fun remove(
        key: String
    ) {
        sharedPreferences.edit().remove(key).apply()
    }

    /**
     * Method to check whether the [PrefsWorker] instance with the current path has stored any value with the specified
     * key
     *
     * @param key The key to check if has been previously stored
     *
     * @return whether the specified key has been previously stored as [Boolean]
     */
    actual fun hasKey(
        key: String
    ): Boolean {
        return sharedPreferences.contains(key)
    }

    /**
     * Method to clear the all preferences specified by the path
     */
    actual fun clearAll() {
        sharedPreferences.edit().clear().apply()
    }

}