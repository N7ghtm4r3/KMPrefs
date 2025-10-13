package com.tecknobit.kmprefs.util

import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

/**
 * Method to correct resolve a retrieved value.
 *
 * When the returned value from the retrieval is `null` or is equal to the [defValue] will be not decoded and directly returned,
 * otherwise will be decoded and then returner
 *
 * @param defValue Is the value to return whether the searched one does not exist
 * @param deserializer Custom deserializer used to deserialize the data after retrieving it
 *
 * @return retrieved value as nullable [T]
 *
 * @param T The type of the retrieved value
 *
 * @since 1.1.0
 */
inline fun <reified T> String?.resolveRetrieval(
    defValue: T? = null,
    deserializer: KSerializer<T> = serializer(),
): T? {
    return if(this == null || this == defValue.toString())
        defValue
    else {
        Json.decodeFromString(
            deserializer = deserializer,
            string = this
        )
    }
}