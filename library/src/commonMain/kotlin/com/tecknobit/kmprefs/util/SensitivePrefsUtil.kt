package com.tecknobit.kmprefs.util

import com.tecknobit.kassaforte.key.genspec.BlockMode
import com.tecknobit.kassaforte.key.genspec.BlockMode.CBC
import com.tecknobit.kassaforte.key.genspec.EncryptionPadding.PKCS7
import com.tecknobit.kassaforte.services.KassaforteSymmetricService

// TODO: TO DOCU
inline fun String.resolveAlias(): String {
    return hashCode().toHexString()
}

// TODO: TO DOCU
suspend inline fun encryptPref(
    alias: String,
    value: String?
) : String? {
    if(value == null)
        return value
    return KassaforteSymmetricService.encrypt(
        alias = alias,
        blockMode = CBC,
        padding = PKCS7,
        data = value
    )
}

// TODO: TO DOCU
suspend inline fun decryptPref(
    alias: String,
    value: String,
    blockMode: BlockMode = CBC
) : String {
    return KassaforteSymmetricService.decrypt(
        alias = alias,
        blockMode = blockMode,
        padding = PKCS7,
        data = value
    )
}