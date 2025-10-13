package com.tecknobit.kmprefs.util

import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

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