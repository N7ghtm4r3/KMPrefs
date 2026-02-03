package com.tecknobit.kmprefs.util

import com.tecknobit.kassaforte.key.genspec.BlockMode
import com.tecknobit.kassaforte.key.genspec.BlockMode.CBC
import com.tecknobit.kassaforte.key.genspec.EncryptionPadding.PKCS7
import com.tecknobit.kassaforte.services.KassaforteSymmetricService

/**
 * Method used to resolve the alias of the key used to safeguard sensitive data
 *
 * @return the alias of the key as [String]
 *
 * @since 1.1.0
 */
internal fun String.resolveAlias(): String {
    return hashCode().toHexString()
}

/**
 * Method used to encrypt a sensitive preference
 *
 * @param alias The alias of the key to use
 * @param value The value of the preference to encrypt
 *
 * @return the preference encrypted as nullable [String], is `null` when the original value is `null`
 *
 * @since 1.1.0
 */
internal suspend inline fun encryptPref(
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

/**
 * Method used to decrypt a sensitive preference
 *
 * @param alias The alias of the key to use
 * @param value The value of the preference to decrypt
 * @param blockMode The type of the block to use to decrypt a sensitive preference
 *
 * @return the preference decrypted as [String]
 *
 * @since 1.1.0
 */
internal suspend inline fun decryptPref(
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