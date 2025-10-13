package com.tecknobit.kmprefs

/**
 * The `PrefsWorker` class helps to manage the preferences storing the data locally using the built-in mechanism for
 * each platform
 *
 * @param path Is the path where store the data
 *
 * @author N7ghtm4r3 - Tecknobit
 */
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class PrefsWorker(
    path: String
) {

    /**
     * `sensitiveKeyAlias` the alias of the key used to encrypt and decrypt sensitive data
     */
    internal val sensitiveKeyAlias: String

    /**
     * Method to locally store a value
     *
     * @param key Is the key of the value
     * @param value Is the value to store
     * @param isSensitive Whether the value to store needs to be protected due to its sensitivity
     * 
     * @param T The type of the value
     */
    fun <T> store(
        key: String,
        value: T?,
        isSensitive: Boolean = false
    )

    /**
     * Method to locally retrieve a value
     *
     * @param key Is the key of the value to retrieve
     * @param defValue Is the value to return whether the searched one does not exist
     * @param isSensitive Whether the value to retrieve was protected due to its sensitivity
     * 
     * @return retrieved value as nullable [String]
     * 
     * @param T The type of the value
     */
    fun <T> retrieve(
        key: String,
        defValue: T?,
        isSensitive: Boolean = false
    ) : String?

    /**
     * Method to locally retrieve a value and then consume it. This method is useful when the project targets also `Web`
     * platform and when [isSensitive] is `true` to correctly use the decrypted data before using it, otherwise is
     * suggested just to use [retrieve] method
     *
     * @param key Is the key of the value to retrieve
     * @param defValue Is the value to consume whether the searched one does not exist
     * @param isSensitive Whether the value to consume was protected due to its sensitivity
     * @param consume The routine executed to consume the retrieved value
     *
     * @param T The type of the value
     *
     * @since 1.1.0
     */
    fun <T> consumeRetrieval(
        key: String,
        defValue: T?,
        isSensitive: Boolean = false,
        consume: (String?) -> Unit
    )

    /**
     * Method to remove locally a value by its key
     *
     * @param key Is the key of the value to remove
     */
    fun remove(
        key: String
    )

    /**
     * Method to check whether the [PrefsWorker] instance with the current path has stored any value with the specified
     * key
     *
     * @param key The key to check if has been previously stored
     *
     * @return whether the specified key has been previously stored as [Boolean]
     */
    fun hasKey(
        key: String
    ) : Boolean

    /**
     * Method to clear the all preferences specified by the path
     */
    fun clearAll()

}