package com.tecknobit.kmprefs

/**
 * The **PrefsWorker** class helps to manage the preferences storing the data locally using the [NSUserDefaults]
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
     * **userDefaults** -> the instance used to manage locally the preferences on `iOs`
     */
    private val userDefaults = NSUserDefaults.standardUserDefaults()

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
        userDefaults.setObject(
            value = value,
            forKey = key
        )
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
        return userDefaults.objectForKey(
            forKey = key
        )
    }

    /**
     * Method to remove locally a value by its key
     *
     * @param key Is the key of the value to remove
     */
    actual fun remove(
        key: String
    ) {
        return userDefaults.removeObjectForKey(
            key = key
        )
    }

    /**
     * Method to clear the all preferences specified by the path
     */
    actual fun clearAll() {
        userDefaults.removePersistentDomainForName(userDefaults.applicationDomain)
    }

}