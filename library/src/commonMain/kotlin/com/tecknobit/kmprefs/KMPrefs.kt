@file:OptIn(ExperimentalSerializationApi::class)

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
import kotlinx.serialization.builtins.ListSerializer
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
     * @param serializer Custom serializer used to serialize the data before storing it
     * @param isSensitive Whether the data to store needs to be protected due to its sensitivity
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
     * @param defValue Is the value to return whether the searched one does not exist
     * @param deserializer Custom deserializer used to deserialize the data after retrieving it
     * @param isSensitive Whether the data to retrieve was protected due to its sensitivity
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

    /**
     * Method to locally retrieve and then consume a [T] value. This method is useful when the project targets also `Web`
     * platform and when [isSensitive] is `true` to correctly use the decrypted data before using it, otherwise is
     * suggested just to use [retrieve] method
     *
     * @param key Is the key of the generic value to retrieve
     * @param defValue Is the value to return whether the searched one does not exist
     * @param deserializer Custom deserializer used to deserialize the data after retrieving it
     * @param isSensitive Whether the data to retrieve was protected due to its sensitivity
     * @param consume The routine executed to consume the retrieved value
     *
     * @param T The type of the value to retrieve
     *
     * @since 1.1.0
     */
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
            consume = { storedValue ->
                val retrieval: T? = storedValue.resolveRetrieval(
                    defValue = defValue,
                    deserializer = deserializer
                )
                consume(retrieval)
            }
        )
    }

    /**
     * Method to add an [element] to a local stored collection
     * 
     * @param element The element to add to the local stored collection
     * @param key Is the key of the collection to add element
     * @param isSensitive Whether the data of the collection was protected due to their sensitivity
     * 
     * @throws IllegalStateException When the key is not associated with a valid collection
     * 
     * @since 1.2.0
     */
    inline fun <reified T> addToCollection(
        element: T,
        key: String,
        isSensitive: Boolean = false
    ) {
        useMutableCollection(
            key = key,
            isSensitive = isSensitive,
            usage = { collection ->
                collection.add(element)
            }
        )
    }

    /**
     * Method to remove an [element] from a local stored collection
     *
     * @param element The element to remove from the local stored collection
     * @param key Is the key of the collection from remove element
     * @param isSensitive Whether the data of the collection was protected due to their sensitivity
     *
     * @throws IllegalStateException When the key is not associated with a valid collection
     *
     * @since 1.2.0
     */
    inline fun <reified T> removeFromCollection(
        element: T,
        key: String,
        isSensitive: Boolean = false
    ) {
        useMutableCollection(
            key = key,
            isSensitive = isSensitive,
            usage = { collection ->
                collection.remove(element)
            }
        )
    }

    /**
     * Method to add [elements] to a local stored collection
     * 
     * @param elements The elements to add to the local stored collection
     * @param key Is the key of the collection where add elements
     * @param isSensitive Whether the data of the collection was protected due to their sensitivity
     * 
     * @throws IllegalStateException When the key is not associated with a valid collection
     * 
     * @since 1.2.0
     */
    inline fun <reified T> addAllToCollection(
        elements: Collection<T>,
        key: String,
        isSensitive: Boolean = false
    ) {
        useMutableCollection(
            key = key,
            isSensitive = isSensitive,
            usage = { collection ->
                collection.addAll(elements)
            }
        )
    }

    /**
     * Method to remove [elements] from a local stored collection
     * 
     * @param elements The elements to remove from the local stored collection
     * @param key Is the key of the collection from remove elements
     * @param isSensitive Whether the data of the collection was protected due to their sensitivity
     * 
     * @throws IllegalStateException When the key is not associated with a valid collection
     * 
     * @since 1.2.0
     */
    inline fun <reified T> removeAllFromCollection(
        elements: Collection<T>,
        key: String,
        isSensitive: Boolean = false
    ) {
        useMutableCollection(
            key = key,
            isSensitive = isSensitive,
            usage = { collection ->
                collection.removeAll(elements.toSet())
            }
        )
    }

    /**
     * Method used to insert an [element] into a locally stored collection or replace the first element with the same
     * selector value
     *
     * @param element The element to insert or replace
     * @param elementSelector The selector used to identify matching elements
     * @param key The key of the locally stored collection
     * @param isSensitive Whether the collection data was protected due to its sensitivity
     * @param T The type of the collection elements
     * @param S The type of the selector value
     *
     * @since 1.2.1
     */
    inline fun <reified T, S> upsertToCollection(
        element: T,
        elementSelector: (T) -> S,
        key: String,
        isSensitive: Boolean = false
    ) {
        useMutableCollection(
            key = key,
            isSensitive = isSensitive,
            usage = { collection ->
                val supportCollection = collection.toMutableList()
                val index = collection.resolveUpsertTargetIndex(
                    elementSelector = elementSelector,
                    selectorTarget = elementSelector(element)
                )

                if(index < 0)
                    supportCollection.add(element)
                else
                    supportCollection[index] = element

                collection.clear()
                collection.addAll(supportCollection)
            }
        )
    }

    /**
     * Method used to find the first collection element matching the [selectorTarget]
     *
     * @param elementSelector The selector used to obtain the value of each element
     * @param selectorTarget The selector value to match
     * @param T The type of the collection elements
     * @param S The type of the selector value
     *
     * @return the index of the first matching element or `-1` when no element matches as [Int]
     *
     * @since 1.2.1
     */
    inline fun <reified T, S> MutableCollection<T>.resolveUpsertTargetIndex(
        elementSelector: (T) -> S,
        selectorTarget: S
    ): Int {
        return indexOfFirst { element ->
            selectorTarget == elementSelector(element)
        }
    }

    /**
     * This method provides a way to access to a local stored collection and use it with a custom [usage].
     * 
     * @param key Is the key of the collection value to retrieve
     * @param isSensitive Whether the data of the collection was protected due to their sensitivity
     * @param usage The custom usage to perform on the retrieved collection
     * 
     * @throws IllegalStateException When the key is not associated with a valid collection
     * 
     * @since 1.2.0
     */
    inline fun <reified T> useMutableCollection(
        key: String,
        isSensitive: Boolean = false,
        usage: (MutableCollection<T>) -> Unit
    ) {
        val deserializer = ListSerializer(
            elementSerializer = serializer<T>()
        )

        var collection = retrieve(
            key = key,
            deserializer = deserializer,
            isSensitive = isSensitive
        )

        collection = collection.guaranteeExistence(
            key = key,
            deserializer = deserializer,
            isSensitive = isSensitive
        )

        val tempCollection = collection.toMutableList()
        usage(tempCollection)
        store(
            key = key,
            value = tempCollection,
            serializer = deserializer,
            isSensitive = isSensitive
        )
    }

    /**
     * Method used to return the retrieved collection or store and return an empty collection when it does not exist
     *
     * @receiver The retrieved collection
     * @param key The key of the locally stored collection
     * @param deserializer The serializer used to store an empty collection
     * @param isSensitive Whether the collection data needs to be protected due to its sensitivity
     * @param T The type of the collection elements
     *
     * @return the existing or newly stored empty collection as [List] of [T]
     *
     * @since 1.2.1
     */
    inline fun <reified T> List<T>?.guaranteeExistence(
        key: String,
        deserializer: KSerializer<List<T>>,
        isSensitive: Boolean = false
    ): List<T> {
        if(this != null)
            return this

        val supportCollection = emptyList<T>()
        store(
            key = key,
            value = supportCollection,
            serializer = deserializer,
            isSensitive = isSensitive
        )

        return supportCollection
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