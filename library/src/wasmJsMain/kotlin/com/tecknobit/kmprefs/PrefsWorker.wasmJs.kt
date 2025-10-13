@file:OptIn(ExperimentalCoroutinesApi::class)

package com.tecknobit.kmprefs

import com.tecknobit.kassaforte.key.genspec.BlockMode.CTR
import com.tecknobit.kmprefs.util.decryptPref
import com.tecknobit.kmprefs.util.encryptPref
import com.tecknobit.kmprefs.util.resolveAlias
import kotlinx.browser.window
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
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
     * `workerScope` the scope of the worker used to execute background operations
     */
    private val workerScope: CoroutineScope = CoroutineScope(
        context = Dispatchers.Main
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
        if(value == null) {
            remove(
                key = key
            )
        } else {
            val valueToStore = if(value is Enum<*>)
                value.name
            else
                value.toString()
            if(isSensitive) {
                workerScope.launch {
                    val encryptedValue = encryptPref(
                        alias = sensitiveKeyAlias,
                        value = valueToStore
                    )!!
                    locallyStore(
                        key = key,
                        value = encryptedValue,
                    )
                }
            } else {
                locallyStore(
                    key = key,
                    value = valueToStore
                )
            }
        }
    }

    /**
     * Core method to locally store a value
     *
     * @param key Is the key of the value
     * @param value Is the value to store
     */
    private fun locallyStore(
        key: String,
        value: String
    ) {
        localStorage.setItem(
            key = key,
            value = value
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
     *
     * #### API Note
     *
     * The [isSensitive] params will be ignored in this method and will be returned encrypted if was a sensitive data.
     * Use the [consumeRetrieval] method to retrieve and then correctly consume the decrypted data
     */
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
            defValue = null,
            isSensitive = isSensitive
        )
        if(!isSensitive || storedValue == null)
            consume(storedValue)
        else {
            workerScope.launch {
                val decryptedValue = decryptPref(
                    alias = sensitiveKeyAlias,
                    value = storedValue,
                    blockMode = CTR
                )
                consume(decryptedValue)
            }
        }
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