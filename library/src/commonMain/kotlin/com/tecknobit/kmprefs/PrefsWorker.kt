package com.tecknobit.kmprefs

/**
 * The **PrefsWorker** class helps to manage the preferences storing the data locally using the built-in mechanism for
 * each platform
 *
 * @param path Is the path where store the data
 *
 * @author N7ghtm4r3 - Tecknobit
 */
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
internal expect class PrefsWorker(
    path: String
) {

    /**
     * Method to store locally a value
     *
     * @param key Is the key of the value
     * @param value Is the value to store
     */
    fun <T> store(
        key: String,
        value: T?
    )

    /**
     * Method to fetch locally a  value
     *
     * @param key Is the key of the value to fetch
     * @param defValue Is the value to return if the searched one does not exist
     * @return fetched value as [String]
     */
    fun <T> fetch(
        key: String,
        defValue: T?
    ) : String?

    /**
     * Method to remove locally a value by its key
     *
     * @param key Is the key of the value to remove
     */
    fun remove(
        key: String
    )

    /**
     * Method to clear the all preferences specified by the path
     */
    fun clearAll()

}