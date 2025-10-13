package com.tecknobit.kmprefs

import com.tecknobit.kmprefs.util.decryptPref
import com.tecknobit.kmprefs.util.encryptPref
import com.tecknobit.kmprefs.util.resolveAlias
import kotlinx.coroutines.runBlocking
import platform.Foundation.NSUserDefaults

/**
 * The `PrefsWorker` class helps to manage the preferences storing the data locally using the [NSUserDefaults]
 * built-in mechanism
 *
 * @param path Is the path where store the data
 *
 * @author N7ghtm4r3 - Tecknobit
 */
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class PrefsWorker actual constructor(
    private val path: String
) {

    /**
     * `sensitiveKeyAlias` the alias of the key used to encrypt and decrypt sensitive data
     */
    internal actual val sensitiveKeyAlias: String = path.resolveAlias()

    /**
     * `userDefaults` the instance used to manage locally the preferences on `iOs`
     */
    private val userDefaults = NSUserDefaults(
        suiteName = path
    )

    /**
     * Method to locally store a value
     *
     * @param key Is the key of the value
     * @param value Is the value to store
     * @param isSensitive Whether the value to store needs to be protected due to its sensitivity
     *
     * @param T The type of the value
     */
    actual fun <T> store(
        key: String,
        value: T?,
        isSensitive: Boolean,
    ) {
        var valueToStore = value?.toString()
        if(isSensitive) {
            runBlocking {
                valueToStore = encryptPref(
                    alias = sensitiveKeyAlias,
                    value = valueToStore
                )
            }
        }
        userDefaults.setObject(
            value = valueToStore,
            forKey = key
        )
    }

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
    actual fun <T> retrieve(
        key: String,
        defValue: T?,
        isSensitive: Boolean,
    ): String? {
        val storedValue = userDefaults.stringForKey(key) ?: return defValue?.toString()
        return if(isSensitive) {
            runBlocking {
                decryptPref(
                    alias = sensitiveKeyAlias,
                    value = storedValue
                )
            }
        } else
            storedValue
    }

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
    actual fun <T> consumeRetrieval(
        key: String,
        defValue: T?,
        isSensitive: Boolean,
        consume: (String?) -> Unit,
    ) {
        val storedValue = retrieve(
            key = key,
            defValue = defValue,
            isSensitive = isSensitive
        )
        consume(storedValue)
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
            defaultName = key
        )
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
        return userDefaults.dataForKey(
            defaultName = key
        ) != null
    }

    /**
     * Method to clear the all preferences specified by the path
     */
    actual fun clearAll() {
        userDefaults.removePersistentDomainForName(
            domainName = path
        )
    }

}