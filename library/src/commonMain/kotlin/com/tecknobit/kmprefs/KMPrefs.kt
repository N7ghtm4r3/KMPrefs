@file:OptIn(ExperimentalSerializationApi::class, ExperimentalUnsignedTypes::class, ExperimentalUnsignedTypes::class)

package com.tecknobit.kmprefs

import com.tecknobit.kassaforte.key.genspec.Algorithm
import com.tecknobit.kassaforte.key.genspec.BlockMode.CBC
import com.tecknobit.kassaforte.key.genspec.EncryptionPadding.PKCS7
import com.tecknobit.kassaforte.key.genspec.KeySize.S256
import com.tecknobit.kassaforte.key.genspec.SymmetricKeyGenSpec
import com.tecknobit.kassaforte.key.usages.KeyPurposes
import com.tecknobit.kassaforte.services.KassaforteSymmetricService
import com.tecknobit.kmprefs.util.resolveAlias
import com.tecknobit.kmprefs.util.resolveRetrieval
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

/**
 * The `KMPrefs` class helps to manage the preferences storing the data locally
 *
 * @param path Is the path where store the data
 *
 * @author N7ghtm4r3 - Tecknobit
 */
// TODO: TO DOCU CHANGES AND ADD 1.1.0
class KMPrefs(
    path: String
) {

    init {
        try {
            KassaforteSymmetricService.generateKey(
                algorithm = Algorithm.AES,
                alias = path.resolveAlias(),
                keyGenSpec = SymmetricKeyGenSpec(
                    keySize = S256,
                    encryptionPadding = PKCS7,
                    blockMode = CBC
                ),
                purposes = KeyPurposes(
                    canEncrypt = true,
                    canDecrypt = true
                )
            )
        } catch (_: Exception) {
        }
    }

    /**
     * `prefsWorker` The implementation of each platform of their preferences management
     */
    val prefsWorker = PrefsWorker(
        path = path
    )

    /**
     * Method to locally store a [T] value
     *
     * @param key Is the key of the generic value
     * @param value Is the value to store
     *
     * @param T The type of the enum to store
     */
    inline fun <reified T> store(
        key: String,
        value: T?,
        serializer: KSerializer<T> = serializer(),
        isSensitive: Boolean = false
    ) {
        if(value == null) {
            removeValue(key)
            return
        }
        prefsWorker.store(
            key = key,
            value = Json.encodeToString(
                serializer = serializer,
                value = value,
            ),
            isSensitive = isSensitive
        )
    }

    /**
     * Method to locally retrieve a [T] value
     *
     * @param key Is the key of the generic value to retrieve
     * @param defValue Is the value to return if the searched one does not exist
     *
     * @return retrieved value as nullable [T]
     *
     * @param T The type of the value to retrieve
     */
    inline fun <reified T> retrieve(
        key: String,
        defValue: T? = null,
        deserializer: KSerializer<T> = serializer(),
        isSensitive: Boolean = false
    ): T? {
        if(!hasKey(key))
            return defValue
        val storedValue = prefsWorker.retrieve(
            key = key,
            defValue = defValue,
            isSensitive = isSensitive
        )
        return storedValue.resolveRetrieval(
            defValue = defValue,
            deserializer = deserializer
        )
    }

    inline fun <reified T> consumeRetrieval(
        key: String,
        defValue: T? = null,
        deserializer: KSerializer<T> = serializer(),
        isSensitive: Boolean = false,
        crossinline consume: (T?) -> Unit
    ) {
        prefsWorker.consumeRetrieval(
            key = key,
            defValue = defValue,
            isSensitive = isSensitive,
            usage = { storedValue ->
                val retrieval: T? = storedValue.resolveRetrieval(
                    defValue = defValue,
                    deserializer = deserializer
                )
                consume(retrieval)
            }
        )
    }

    /**
     * Method to remove locally a value by its key
     *
     * @param key Is the key of the value to remove
     */
    fun removeValue(
        key: String
    ) {
        prefsWorker.remove(
            key = key
        )
    }

    /**
     * Method to check whether the [KMPrefs] instance with the current path has stored any value with the specified
     * key
     *
     * @param key The key to check if has been previously stored
     *
     * @return whether the specified key has been previously stored as [Boolean]
     */
    fun hasKey(
        key: String
    ): Boolean {
        return prefsWorker.hasKey(
            key = key
        )
    }

    /**
     * Method to clear the all preferences specified by the path
     */
    fun clearAll() {
        prefsWorker.clearAll()
    }

}