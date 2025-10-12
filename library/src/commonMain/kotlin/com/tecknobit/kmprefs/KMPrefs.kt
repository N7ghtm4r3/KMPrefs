package com.tecknobit.kmprefs

import com.tecknobit.kassaforte.key.genspec.Algorithm
import com.tecknobit.kassaforte.key.genspec.BlockMode.CBC
import com.tecknobit.kassaforte.key.genspec.EncryptionPadding.PKCS7
import com.tecknobit.kassaforte.key.genspec.KeySize.S256
import com.tecknobit.kassaforte.key.genspec.SymmetricKeyGenSpec
import com.tecknobit.kassaforte.key.usages.KeyPurposes
import com.tecknobit.kassaforte.services.KassaforteSymmetricService
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

/**
 * The `KMPrefs` class helps to manage the preferences storing the data locally
 *
 * @param path Is the path where store the data
 *
 * @author N7ghtm4r3 - Tecknobit
 */
class KMPrefs(
    val path: String
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
     * `prefsWorker` the implementation of each platform of their preferences management
     */
    private val prefsWorker = PrefsWorker(
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
        isSensitive: Boolean = false
    ) {
        reifiedUse {
            store(
                key = key,
                value = Json.encodeToString(value),
                isSensitive = isSensitive
            )
        }
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
        isSensitive: Boolean = false
    ): T? {
        return reifiedUse {
            val localEntry = retrieve(
                key = key,
                defValue = defValue,
                isSensitive = isSensitive
            )
            if(localEntry == null)
                defValue
            else
                Json.decodeFromString(localEntry)
        }
    }

    /**
     * Method to use [KMPrefs] with a reified type
     * 
     * @param usage The reified routine to work with a reified type
     * 
     * @param T The type of the reified item
     * 
     * @return the result of the routine execution as [T]
     */
    inline fun <reified T> reifiedUse(
        crossinline usage: PrefsWorker.() -> T
    ): T {
        val prefsWorker = PrefsWorker(
            path = path
        )
        return usage(prefsWorker)
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
     * Method to check whether the value with the specified key matches to the [matcher] value
     *
     * @param key The key of the value to check
     * @param matcher The value to use as matcher on the comparison
     *
     * @return whether the values match as [Boolean]
     *
     */
    @ExperimentalUnsignedTypes
    fun <T> valueMatchesTo(
        key: String,
        matcher: T?,
        isSensitive: Boolean = false
    ) : Boolean {
        return when (matcher) {
            is BooleanArray -> {
                val array = deserializeData(
                    key = key,
                    defValue = booleanArrayOf(),
                    isSensitive = isSensitive
                )
                matcher.contentEquals(array)
            }
            is ByteArray -> {
                val array = deserializeData(
                    key = key,
                    defValue = byteArrayOf(),
                    isSensitive = isSensitive
                )
                matcher.contentEquals(array)
            }
            is UByteArray -> {
                val array = deserializeData(
                    key = key,
                    defValue = ubyteArrayOf(),
                    isSensitive = isSensitive
                )
                matcher.contentEquals(array)
            }
            is ShortArray -> {
                val array = deserializeData(
                    key = key,
                    defValue = shortArrayOf(),
                    isSensitive = isSensitive
                )
                matcher.contentEquals(array)
            }
            is UShortArray -> {
                val array = deserializeData(
                    key = key,
                    defValue = ushortArrayOf(),
                    isSensitive = isSensitive
                )
                matcher.contentEquals(array)
            }
            is IntArray -> {
                val array = deserializeData(
                    key = key,
                    defValue = intArrayOf(),
                    isSensitive = isSensitive
                )
                matcher.contentEquals(array)
            }
            is UIntArray -> {
                val array = deserializeData(
                    key = key,
                    defValue = uintArrayOf(),
                    isSensitive = isSensitive
                )
                matcher.contentEquals(array)
            }
            is LongArray -> {
                val array = deserializeData(
                    key = key,
                    defValue = longArrayOf(),
                    isSensitive = isSensitive
                )
                matcher.contentEquals(array)
            }
            is ULongArray -> {
                val array = deserializeData(
                    key = key,
                    defValue = ulongArrayOf(),
                    isSensitive = isSensitive
                )
                matcher.contentEquals(array)
            }
            is FloatArray -> {
                val array = deserializeData(
                    key = key,
                    defValue = floatArrayOf(),
                    isSensitive = isSensitive
                )
                matcher.contentEquals(array)
            }
            is DoubleArray -> {
                val array = deserializeData(
                    key = key,
                    defValue = doubleArrayOf(),
                    isSensitive = isSensitive
                )
                matcher.contentEquals(array)
            }
            else -> {
                val value = prefsWorker.retrieve(
                    key = key,
                    defValue = null,
                    isSensitive = isSensitive
                )
                value == matcher.toString()
            }
        }
    }

    /**
     * Method to deserialize raw json data into a [T] object
     *
     * @param key Is the key of the object to retrieve
     * @param defValue Is the value to return if the searched one does not exist
     *
     * @return object as [T]
     */
    private inline fun <reified T> deserializeData(
        key: String,
        defValue: T?,
        isSensitive: Boolean = false
    ) : T? {
        val array = prefsWorker.retrieve(
            key = key,
            defValue = if(defValue != null)
                Json.encodeToString(defValue)
            else
                null,
            isSensitive = isSensitive
        )
        if(array == null)
            return null
        return Json.decodeFromString<T>(array)
    }

    /**
     * Method to check whether the custom object with the specified key matches to the [matcher] value
     *
     * @param key The key of the custom object to check
     * @param deserializer The custom deserializer for the custom object used in the compare
     * @param matcher The custom object to use as matcher on the comparison
     *
     * @return whether the custom objects match as [Boolean]
     *
     */
    inline fun <reified T> customObjectMatchesTo(
        key: String,
        deserializer: KSerializer<T> = serializer(),
        matcher: T?
    ) : Boolean {
        val value :T? = retrieveCustomObject(
            key = key,
            deserializer = deserializer
        )
        return value == matcher
    }

    /**
     * Method to locally store a custom [Serializable] object
     *
     * @param key Is the key of the custom object
     * @param serializer The custom serializer for the custom object used to store it locally
     * @param value Is the value to store
     */
    @ExperimentalSerializationApi
    inline fun <reified T> storeCustomObject(
        key: String,
        serializer: KSerializer<T> = serializer(),
        value: T?
    ) {
        if(value == null) {
            removeValue(
                key = key
            )
            return
        }
        store(
            key = key,
            value = Json.encodeToString(serializer, value)
        )
    }

    /**
     * Method to locally retrieve a custom [Serializable] object
     *
     * @param key Is the key of the custom object
     * @param deserializer The custom deserializer for the custom object used to retrieve
     * @param defValue - Is the value to return if the searched one does not exist
     *
     * @return the custom object locally stored as [T]
     */
    inline fun <reified T> retrieveCustomObject(
        key: String,
        deserializer: KSerializer<T> = serializer(),
        defValue: T? = null
    ) : T? {
        if(!hasKey(key))
            return defValue
        return Json.decodeFromString(
            deserializer = deserializer,
            string = retrieve(
                key = key
            )!!
        )
    }

    /**
     * Method to clear the all preferences specified by the path
     */
    fun clearAll() {
        prefsWorker.clearAll()
    }

}