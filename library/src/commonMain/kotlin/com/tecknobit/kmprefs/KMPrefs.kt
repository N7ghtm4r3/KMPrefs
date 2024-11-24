package com.tecknobit.kmprefs

/**
 * The **KMPrefs** class helps to manage the preferences storing the data locally
 *
 * @param path Is the path where store the data
 *
 * @author N7ghtm4r3 - Tecknobit
 */
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class KMPrefs(
    path: String
) {

    /**
     * Method to store locally a [String] value
     *
     * @param key Is the key of the string
     * @param value Is the value to store
     */
    fun storeString(
        key: String,
        value: String?
    )

    /**
     * Method to fetch locally a [String] value
     *
     * @param key Is the key of the string to fetch
     * @param defValue Is the value to return if the searched one does not exist
     */
    fun fetchString(
        key: String,
        defValue: String? = null
    ): String?

    /**
     * Method to remove locally a [String] value by its key
     *
     * @param key Is the key of the string to remove
     */
    fun removeString(
        key: String
    )

    /**
     * Method to clear the all preferences specified by the path
     */
    fun clearAll()

}