package com.tecknobit.kmprefs

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * The **KMPrefs** class helps to manage the preferences storing the data locally
 *
 * @param path Is the path where store the data
 *
 * @author N7ghtm4r3 - Tecknobit
 */
class KMPrefs(
    path: String
) {

    /**
     * **prefsWorker** -> the implementation of each platform of their preferences management
     */
    private val prefsWorker = PrefsWorker(
        path = path
    )

    /**
     * Method to store locally a [Boolean] value
     *
     * @param key Is the key of the boolean
     * @param value Is the value to store
     */
    fun storeBoolean(
        key: String,
        value: Boolean?
    ) {
        prefsWorker.store(
            key = key,
            value = value
        )
    }

    /**
     * Method to retrieve locally a [Boolean] value
     *
     * @param key Is the key of the boolean to retrieve
     * @param defValue Is the value to return if the searched one does not exist
     * @return retrieved value as [Boolean]
     *
     */
    fun retrieveBoolean(
        key: String,
        defValue: Boolean? = null
    ): Boolean {
        return prefsWorker.retrieve(
            key = key,
            defValue = defValue
        ).toBoolean()
    }

    /**
     * Method to store locally a [BooleanArray] value
     *
     * @param key Is the key of the boolean array
     * @param value Is the value to store
     */
    fun storeBooleanArray(
        key: String,
        value: BooleanArray?
    ) {
        prefsWorker.store(
            key = key,
            value = value?.contentToString()
        )
    }

    /**
     * Method to retrieve locally a [BooleanArray] value
     *
     * @param key Is the key of the boolean array to retrieve
     * @param defValue Is the value to return if the searched one does not exist
     * @return retrieved value as [BooleanArray]
     */
    fun retrieveBooleanArray(
        key: String,
        defValue: BooleanArray? = null
    ): BooleanArray? {
        return deserializeData(
            key = key,
            defValue = defValue
        )
    }

    /**
     * Method to store locally a [Byte] value
     *
     * @param key Is the key of the byte
     * @param value Is the value to store
     */
    fun storeByte(
        key: String,
        value: Byte?
    ) {
        prefsWorker.store(
            key = key,
            value = value
        )
    }

    /**
     * Method to retrieve locally a [Byte] value
     *
     * @param key Is the key of the byte to retrieve
     * @param defValue Is the value to return if the searched one does not exist
     * @return retrieved value as [Byte]
     */
    fun retrieveByte(
        key: String,
        defValue: Byte? = null
    ): Byte? {
        return prefsWorker.retrieve(
            key = key,
            defValue = defValue
        )?.toByteOrNull()
    }

    /**
     * Method to store locally a [ByteArray] value
     *
     * @param key Is the key of the byte array
     * @param value Is the value to store
     */
    fun storeByteArray(
        key: String,
        value: ByteArray?
    ) {
        prefsWorker.store(
            key = key,
            value = value?.contentToString()
        )
    }

    /**
     * Method to retrieve locally a [ByteArray] value
     *
     * @param key Is the key of the byte array to retrieve
     * @param defValue Is the value to return if the searched one does not exist
     * @return retrieved value as [ByteArray]
     */
    fun retrieveByteArray(
        key: String,
        defValue: ByteArray? = null
    ): ByteArray? {
        return deserializeData(
            key = key,
            defValue = defValue
        )
    }

    /**
     * Method to store locally a [UByte] value
     *
     * @param key Is the key of the unsigned byte
     * @param value Is the value to store
     */
    fun storeUnsignedByte(
        key: String,
        value: UByte?
    ) {
        prefsWorker.store(
            key = key,
            value = value
        )
    }

    /**
     * Method to retrieve locally a [UByte] value
     *
     * @param key Is the key of the unsigned byte to retrieve
     * @param defValue Is the value to return if the searched one does not exist
     * @return retrieved value as [UByte]
     */
    fun retrieveUnsignedByte(
        key: String,
        defValue: UByte? = null
    ): UByte? {
        return prefsWorker.retrieve(
            key = key,
            defValue = defValue
        )?.toUByteOrNull()
    }

    /**
     * Method to store locally a [UByteArray] value
     *
     * @param key Is the key of the unsigned byte array
     * @param value Is the value to store
     */
    @OptIn(ExperimentalUnsignedTypes::class)
    fun storeUnsignedByteArray(
        key: String,
        value: UByteArray?
    ) {
        prefsWorker.store(
            key = key,
            value = value?.contentToString()
        )
    }

    /**
     * Method to retrieve locally a [UByteArray] value
     *
     * @param key Is the key of the unsigned byte array to retrieve
     * @param defValue Is the value to return if the searched one does not exist
     * @return retrieved value as [UByteArray]
     */
    @OptIn(ExperimentalUnsignedTypes::class)
    fun retrieveUnsignedByteArray(
        key: String,
        defValue: UByteArray? = null
    ): UByteArray? {
        return deserializeData(
            key = key,
            defValue = defValue
        )
    }

    /**
     * Method to store locally a [Short] value
     *
     * @param key Is the key of the short
     * @param value Is the value to store
     */
    fun storeShort(
        key: String,
        value: Short?
    ) {
        prefsWorker.store(
            key = key,
            value = value
        )
    }

    /**
     * Method to retrieve locally a [Short] value
     *
     * @param key Is the key of the short to retrieve
     * @param defValue Is the value to return if the searched one does not exist
     * @return retrieved value as [Short]
     */
    fun retrieveShort(
        key: String,
        defValue: Short? = null
    ): Short? {
        return prefsWorker.retrieve(
            key = key,
            defValue = defValue
        )?.toShortOrNull()
    }

    /**
     * Method to store locally a [ShortArray] value
     *
     * @param key Is the key of the short array
     * @param value Is the value to store
     */
    fun storeShortArray(
        key: String,
        value: ShortArray?
    ) {
        prefsWorker.store(
            key = key,
            value = value?.contentToString()
        )
    }

    /**
     * Method to retrieve locally a [ShortArray] value
     *
     * @param key Is the key of the short array to retrieve
     * @param defValue Is the value to return if the searched one does not exist
     * @return retrieved value as [ShortArray]
     */
    fun retrieveShortArray(
        key: String,
        defValue: ShortArray? = null
    ): ShortArray? {
        return deserializeData(
            key = key,
            defValue = defValue
        )
    }

    /**
     * Method to store locally a [UShort] value
     *
     * @param key Is the key of the unsigned short
     * @param value Is the value to store
     */
    fun storeUnsignedShort(
        key: String,
        value: UShort?
    ) {
        prefsWorker.store(
            key = key,
            value = value
        )
    }

    /**
     * Method to retrieve locally a [UShort] value
     *
     * @param key Is the key of the unsigned short to retrieve
     * @param defValue Is the value to return if the searched one does not exist
     * @return retrieved value as [UShort]
     */
    fun retrieveUnsignedShort(
        key: String,
        defValue: UShort = UShort.MIN_VALUE
    ): UShort? {
        return prefsWorker.retrieve(
            key = key,
            defValue = defValue
        )?.toUShortOrNull()
    }

    /**
     * Method to store locally a [UShortArray] value
     *
     * @param key Is the key of the unsigned short array
     * @param value Is the value to store
     */
    @OptIn(ExperimentalUnsignedTypes::class)
    fun storeUnsignedShortArray(
        key: String,
        value: UShortArray?
    ) {
        prefsWorker.store(
            key = key,
            value = value?.contentToString()
        )
    }

    /**
     * Method to retrieve locally a [UShortArray] value
     *
     * @param key Is the key of the unsigned short array to retrieve
     * @param defValue Is the value to return if the searched one does not exist
     * @return retrieved value as [UShortArray]
     */
    @OptIn(ExperimentalUnsignedTypes::class)
    fun retrieveUnsignedShortArray(
        key: String,
        defValue: UShortArray? = null
    ): UShortArray? {
        return deserializeData(
            key = key,
            defValue = defValue
        )
    }

    /**
     * Method to store locally a [Int] value
     *
     * @param key Is the key of the int
     * @param value Is the value to store
     */
    fun storeInt(
        key: String,
        value: Int?
    ) {
        prefsWorker.store(
            key = key,
            value = value
        )
    }

    /**
     * Method to retrieve locally a [Int] value
     *
     * If [defValue] is not specified and the searched [key] not exists will be returned [Int.MIN_VALUE] as default
     *
     * @param key Is the key of the int to retrieve
     * @param defValue Is the value to return if the searched one does not exist
     * @return retrieved value as [Int]
     */
    fun retrieveInt(
        key: String,
        defValue: Int? = null
    ): Int? {
        return prefsWorker.retrieve(
            key = key,
            defValue = defValue
        )?.toIntOrNull()
    }

    /**
     * Method to store locally a [IntArray] value
     *
     * @param key Is the key of the int array
     * @param value Is the value to store
     */
    fun storeIntArray(
        key: String,
        value: IntArray?
    ) {
        prefsWorker.store(
            key = key,
            value = value?.contentToString()
        )
    }

    /**
     * Method to retrieve locally a [IntArray] value
     *
     * @param key Is the key of the int array to retrieve
     * @param defValue Is the value to return if the searched one does not exist
     * @return retrieved value as [IntArray]
     */
    fun retrieveIntArray(
        key: String,
        defValue: IntArray? = null
    ): IntArray? {
        return deserializeData(
            key = key,
            defValue = defValue
        )
    }

    /**
     * Method to store locally a [UInt] value
     *
     * @param key Is the key of the unsigned int
     * @param value Is the value to store
     */
    fun storeUnsignedInt(
        key: String,
        value: UInt?
    ) {
        prefsWorker.store(
            key = key,
            value = value
        )
    }

    /**
     * Method to retrieve locally a [UInt] value
     *
     * @param key Is the key of the unsigned int to retrieve
     * @param defValue Is the value to return if the searched one does not exist
     * @return retrieved value as [UInt]
     */
    fun retrieveUnsignedInt(
        key: String,
        defValue: UInt? = null
    ): UInt? {
        return prefsWorker.retrieve(
            key = key,
            defValue = defValue
        )?.toUIntOrNull()
    }

    /**
     * Method to store locally a [UIntArray] value
     *
     * @param key Is the key of the unsigned int array
     * @param value Is the value to store
     */
    @OptIn(ExperimentalUnsignedTypes::class)
    fun storeUnsignedIntArray(
        key: String,
        value: UIntArray?
    ) {
        prefsWorker.store(
            key = key,
            value = value?.contentToString()
        )
    }

    /**
     * Method to retrieve locally a [UIntArray] value
     *
     * @param key Is the key of the unsigned int array to retrieve
     * @param defValue Is the value to return if the searched one does not exist
     * @return retrieved value as [UIntArray]
     */
    @OptIn(ExperimentalUnsignedTypes::class)
    fun retrieveUnsignedIntArray(
        key: String,
        defValue: UIntArray? = null
    ): UIntArray? {
        return deserializeData(
            key = key,
            defValue = defValue
        )
    }

    /**
     * Method to store locally a [Float] value
     *
     * @param key Is the key of the float
     * @param value Is the value to store
     */
    fun storeFloat(
        key: String,
        value: Float?
    ) {
        prefsWorker.store(
            key = key,
            value = value
        )
    }

    /**
     * Method to retrieve locally a [Float] value
     *
     * @param key Is the key of the float to retrieve
     * @param defValue Is the value to return if the searched one does not exist
     * @return retrieved value as [Float]
     */
    fun retrieveFloat(
        key: String,
        defValue: Float? = null
    ): Float? {
        return prefsWorker.retrieve(
            key = key,
            defValue = defValue
        )?.toFloatOrNull()
    }

    /**
     * Method to store locally a [FloatArray] value
     *
     * @param key Is the key of the float array
     * @param value Is the value to store
     */
    fun storeFloatArray(
        key: String,
        value: FloatArray?
    ) {
        prefsWorker.store(
            key = key,
            value = value?.contentToString()
        )
    }

    /**
     * Method to retrieve locally a [FloatArray] value
     *
     * @param key Is the key of the float array to retrieve
     * @param defValue Is the value to return if the searched one does not exist
     * @return retrieved value as [FloatArray]
     */
    fun retrieveFloatArray(
        key: String,
        defValue: FloatArray? = null
    ): FloatArray? {
        return deserializeData(
            key = key,
            defValue = defValue
        )
    }

    /**
     * Method to store locally a [Double] value
     *
     * @param key Is the key of the double
     * @param value Is the value to store
     */
    fun storeDouble(
        key: String,
        value: Double?
    ) {
        prefsWorker.store(
            key = key,
            value = value
        )
    }

    /**
     * Method to retrieve locally a [Double] value
     *
     * @param key Is the key of the double to retrieve
     * @param defValue Is the value to return if the searched one does not exist
     * @return retrieved value as [Double]
     */
    fun retrieveDouble(
        key: String,
        defValue: Double? = null
    ): Double? {
        return prefsWorker.retrieve(
            key = key,
            defValue = defValue
        )?.toDoubleOrNull()
    }

    /**
     * Method to store locally a [DoubleArray] value
     *
     * @param key Is the key of the double array
     * @param value Is the value to store
     */
    fun storeDoubleArray(
        key: String,
        value: DoubleArray?
    ) {
        prefsWorker.store(
            key = key,
            value = value?.contentToString()
        )
    }

    /**
     * Method to retrieve locally a [DoubleArray] value
     *
     * @param key Is the key of the double array to retrieve
     * @param defValue Is the value to return if the searched one does not exist
     * @return retrieved value as [DoubleArray]
     */
    fun retrieveDoubleArray(
        key: String,
        defValue: DoubleArray? = null
    ): DoubleArray? {
        return deserializeData(
            key = key,
            defValue = defValue
        )
    }

    /**
     * Method to store locally a [Long] value
     *
     * @param key Is the key of the long
     * @param value Is the value to store
     */
    fun storeLong(
        key: String,
        value: Long?
    ) {
        prefsWorker.store(
            key = key,
            value = value
        )
    }

    /**
     * Method to retrieve locally a [Long] value
     *
     * @param key Is the key of the long to retrieve
     * @param defValue Is the value to return if the searched one does not exist
     *
     * @return retrieved value as [Long]
     */
    fun retrieveLong(
        key: String,
        defValue: Long? = null
    ): Long? {
        return prefsWorker.retrieve(
            key = key,
            defValue = defValue
        )?.toLongOrNull()
    }

    /**
     * Method to store locally a [LongArray] value
     *
     * @param key Is the key of the long array
     * @param value Is the value to store
     */
    fun storeLongArray(
        key: String,
        value: LongArray?
    ) {
        prefsWorker.store(
            key = key,
            value = value?.contentToString()
        )
    }

    /**
     * Method to retrieve locally a [LongArray] value
     *
     * @param key Is the key of the long array to retrieve
     * @param defValue Is the value to return if the searched one does not exist
     *
     * @return retrieved value as [LongArray]
     */
    fun retrieveLongArray(
        key: String,
        defValue: LongArray? = null
    ): LongArray? {
        return deserializeData(
            key = key,
            defValue = defValue
        )
    }

    /**
     * Method to store locally a [ULong] value
     *
     * @param key Is the key of the unsigned long
     * @param value Is the value to store
     */
    fun storeUnsignedLong(
        key: String,
        value: ULong?
    ) {
        prefsWorker.store(
            key = key,
            value = value
        )
    }

    /**
     * Method to retrieve locally a [ULong] value
     *
     * @param key Is the key of the unsigned long to retrieve
     * @param defValue Is the value to return if the searched one does not exist
     *
     * @return retrieved value as [ULong]
     */
    fun retrieveUnsignedLong(
        key: String,
        defValue: ULong? = null
    ): ULong? {
        return prefsWorker.retrieve(
            key = key,
            defValue = defValue
        )?.toULongOrNull()
    }

    /**
     * Method to store locally a [ULongArray] value
     *
     * @param key Is the key of the unsigned long array
     * @param value Is the value to store
     */
    @OptIn(ExperimentalUnsignedTypes::class)
    fun storeUnsignedLongArray(
        key: String,
        value: ULongArray?
    ) {
        prefsWorker.store(
            key = key,
            value = value?.contentToString()
        )
    }

    /**
     * Method to retrieve locally a [ULongArray] value
     *
     * @param key Is the key of the unsigned long array to retrieve
     * @param defValue Is the value to return if the searched one does not exist
     *
     * @return retrieved value as [ULongArray]
     */
    @OptIn(ExperimentalUnsignedTypes::class)
    fun retrieveUnsignedLongArray(
        key: String,
        defValue: ULongArray? = null
    ): ULongArray? {
        return deserializeData(
            key = key,
            defValue = defValue
        )
    }

    /**
     * Method to store locally a [String] value
     *
     * @param key Is the key of the string
     * @param value Is the value to store
     */
    fun storeString(
        key: String,
        value: String?
    ) {
        prefsWorker.store(
            key = key,
            value = value
        )
    }

    /**
     * Method to retrieve locally a [String] value
     *
     * @param key Is the key of the string to retrieve
     * @param defValue Is the value to return if the searched one does not exist
     *
     * @return retrieved value as [String]
     */
    fun retrieveString(
        key: String,
        defValue: String? = null
    ): String? {
        return prefsWorker.retrieve(
            key = key,
            defValue = defValue
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
     * Method to clear the all preferences specified by the path
     */
    fun clearAll() {
        prefsWorker.clearAll()
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
        defValue: T?
    ) : T? {
        val array = prefsWorker.retrieve(
            key = key,
            defValue = if(defValue != null)
                Json.encodeToString(defValue)
            else
                null
        )
        if(array == null)
            return null
        return Json.decodeFromString<T>(array)
    }

}