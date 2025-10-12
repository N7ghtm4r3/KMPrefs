@file:OptIn(ExperimentalCoroutinesApi::class)

package com.tecknobit.kmprefs

import kotlinx.browser.window
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.w3c.dom.DedicatedWorkerGlobalScope

external val self: DedicatedWorkerGlobalScope

/**
 * The `PrefsWorker` class helps to manage the preferences storing the data locally using the [kotlinx.browser.localStorage]
 * built-in mechanism
 *
 * @param path Is the path where store the data
 *
 * @author N7ghtm4r3 - Tecknobit
 */
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class PrefsWorker actual constructor(
    path: String
) {

    /**
     * `sensitiveKeyAlias` the alias of the key used to encrypt and decrypt sensitive data
     */
    internal actual val sensitiveKeyAlias: String = path.resolveAlias()

    /**
     * `localStorage` the instance used to manage locally the preferences on `WEB`
     */
    private val localStorage = window.localStorage

    /**
     * Method to locally store a value
     *
     * @param key Is the key of the value
     * @param value Is the value to store
     */
    // TODO: TO DOCU
    actual fun <T> store(
        key: String,
        value: T?,
        isSensitive: Boolean,
    ) {
        if(value == null) {
            remove(
                key = key
            )
        } else {
            localStorage.setItem(
                key = key,
                value = value.toString()
            )
        }
    }

    /**
     * Method to locally retrieve a value
     *
     * @param key Is the key of the value to retrieve
     * @param defValue Is the value to return if the searched one does not exist
     * @return fetched value as [String]
     */
    // TODO: TO DOCU
    actual fun <T> retrieve(
        key: String,
        defValue: T?,
        isSensitive: Boolean,
    ): String? {
        val value = localStorage.getItem(
            key = key
        )
        return value ?: defValue?.toString()
    }

    actual fun <T> consumeRetrieval(
        key: String,
        defValue: T?,
        isSensitive: Boolean,
        usage: (String?) -> Unit,
    ) {
        val storedValue = retrieve(
            key = key,
            defValue = defValue,
            isSensitive = isSensitive
        )
        usage(storedValue)
    }

    /**
     * Method to remove locally a value by its key
     *
     * @param key Is the key of the value to remove
     */
    actual fun remove(
        key: String
    ) {
        localStorage.removeItem(
            key = key
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
        return retrieve(
            key = key,
            defValue = null
        ) != null
    }

    /**
     * Method to clear the all preferences specified by the path
     */
    actual fun clearAll() {
        localStorage.clear()
    }

}