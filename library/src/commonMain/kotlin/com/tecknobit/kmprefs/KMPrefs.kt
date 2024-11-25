package com.tecknobit.kmprefs

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
     * Method to fetch locally a [Boolean] value
     *
     * @param key Is the key of the boolean to fetch
     * @param defValue Is the value to return if the searched one does not exist
     *
     */
    fun fetchBoolean(
        key: String,
        defValue: Boolean? = null
    ): Boolean {
        return prefsWorker.fetch(
            key = key,
            defValue = defValue
        ).toBoolean()
    }

    /**
     * Method to remove locally a [Boolean] value by its key
     *
     * @param key Is the key of the boolean to remove
     */
    fun removeBoolean(
        key: String
    ) {
        prefsWorker.remove(
            key = key
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
     * Method to fetch locally a [Byte] value
     *
     * @param key Is the key of the byte to fetch
     * @param defValue Is the value to return if the searched one does not exist
     */
    fun fetchByte(
        key: String,
        defValue: Byte? = null
    ): Byte? {
        return prefsWorker.fetch(
            key = key,
            defValue = defValue
        )?.toByteOrNull()
    }

    /**
     * Method to remove locally a [Byte] value by its key
     *
     * @param key Is the key of the byte to remove
     */
    fun removeByte(
        key: String
    ) {
        prefsWorker.remove(
            key = key
        )
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
    }

    /**
     * Method to fetch locally a [ByteArray] value
     *
     * @param key Is the key of the byte array to fetch
     * @param defValue Is the value to return if the searched one does not exist
     */
    fun fetchByteArray(
        key: String,
        defValue: ByteArray? = null
    ): ByteArray? {
        return null
    }

    /**
     * Method to remove locally a [ByteArray] value by its key
     *
     * @param key Is the key of the byte array to remove
     */
    fun removeByteArray(
        key: String
    ) {
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
     * Method to fetch locally a [UByte] value
     *
     * @param key Is the key of the unsigned byte to fetch
     * @param defValue Is the value to return if the searched one does not exist
     */
    fun fetchUnsignedByte(
        key: String,
        defValue: UByte? = null
    ): UByte? {
        return prefsWorker.fetch(
            key = key,
            defValue = defValue
        )?.toUByteOrNull()
    }

    /**
     * Method to remove locally a [UByte] value by its key
     *
     * @param key Is the key of the unsigned byte to remove
     */
    fun removeUnsignedByte(
        key: String
    ) {
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
    }

    /**
     * Method to fetch locally a [UByteArray] value
     *
     * @param key Is the key of the unsigned byte array to fetch
     * @param defValue Is the value to return if the searched one does not exist
     */
    @OptIn(ExperimentalUnsignedTypes::class)
    fun fetchUnsignedByteArray(
        key: String,
        defValue: UByteArray? = null
    ): UByteArray? {
        return null
    }

    /**
     * Method to remove locally a [UByteArray] value by its key
     *
     * @param key Is the key of the unsigned byte array to remove
     */
    fun removeUnsignedByteArray(
        key: String
    ) {
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
     * Method to fetch locally a [Short] value
     *
     * @param key Is the key of the short to fetch
     * @param defValue Is the value to return if the searched one does not exist
     */
    fun fetchShort(
        key: String,
        defValue: Short? = null
    ): Short? {
        return prefsWorker.fetch(
            key = key,
            defValue = defValue
        )?.toShortOrNull()
    }

    /**
     * Method to remove locally a [Short] value by its key
     *
     * @param key Is the key of the short to remove
     */
    fun removeShort(
        key: String
    ) {
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
    }

    /**
     * Method to fetch locally a [ShortArray] value
     *
     * @param key Is the key of the short array to fetch
     * @param defValue Is the value to return if the searched one does not exist
     */
    fun fetchShortArray(
        key: String,
        defValue: ShortArray? = null
    ): ShortArray? {
        return null
    }

    /**
     * Method to remove locally a [ShortArray] value by its key
     *
     * @param key Is the key of the short array to remove
     */
    fun removeShortArray(
        key: String
    ) {
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
     * Method to fetch locally a [UShort] value
     *
     * @param key Is the key of the unsigned short to fetch
     * @param defValue Is the value to return if the searched one does not exist
     */
    fun fetchUnsignedShort(
        key: String,
        defValue: UShort = UShort.MIN_VALUE
    ): UShort? {
        return prefsWorker.fetch(
            key = key,
            defValue = defValue
        )?.toUShortOrNull()
    }

    /**
     * Method to remove locally a [UShort] value by its key
     *
     * @param key Is the key of the unsigned short to remove
     */
    fun removeUnsignedShort(
        key: String
    ) {
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
    }

    /**
     * Method to fetch locally a [UShortArray] value
     *
     * @param key Is the key of the unsigned short array to fetch
     * @param defValue Is the value to return if the searched one does not exist
     */
    @OptIn(ExperimentalUnsignedTypes::class)
    fun fetchUnsignedShortArray(
        key: String,
        defValue: UShortArray? = null
    ): UShortArray? {
        return null
    }

    /**
     * Method to remove locally a [UShortArray] value by its key
     *
     * @param key Is the key of the unsigned short array to remove
     */
    fun removeUnsignedShortArray(
        key: String
    ) {
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
     * Method to fetch locally a [Int] value
     *
     * If [defValue] is not specified and the searched [key] not exists will be returned [Int.MIN_VALUE] as default
     *
     * @param key Is the key of the int to fetch
     * @param defValue Is the value to return if the searched one does not exist
     */
    fun fetchInt(
        key: String,
        defValue: Int? = null
    ): Int? {
        return prefsWorker.fetch(
            key = key,
            defValue = defValue
        )?.toIntOrNull()
    }

    /**
     * Method to remove locally a [Int] value by its key
     *
     * @param key Is the key of the int to remove
     */
    fun removeInt(
        key: String
    ) {
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
    }

    /**
     * Method to fetch locally a [IntArray] value
     *
     * @param key Is the key of the int array to fetch
     * @param defValue Is the value to return if the searched one does not exist
     */
    fun fetchIntArray(
        key: String,
        defValue: IntArray? = null
    ): IntArray? {
        return null
    }

    /**
     * Method to remove locally a [IntArray] value by its key
     *
     * @param key Is the key of the int array to remove
     */
    fun removeIntArray(
        key: String
    ) {
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
     * Method to fetch locally a [UInt] value
     *
     * @param key Is the key of the unsigned int to fetch
     * @param defValue Is the value to return if the searched one does not exist
     */
    fun fetchUnsignedInt(
        key: String,
        defValue: UInt? = null
    ): UInt? {
        return prefsWorker.fetch(
            key = key,
            defValue = defValue
        )?.toUIntOrNull()
    }

    /**
     * Method to remove locally a [UInt] value by its key
     *
     * @param key Is the key of the unsigned int to remove
     */
    fun removeUnsignedInt(
        key: String
    ) {
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
    }

    /**
     * Method to fetch locally a [UIntArray] value
     *
     * @param key Is the key of the unsigned int array to fetch
     * @param defValue Is the value to return if the searched one does not exist
     */
    @OptIn(ExperimentalUnsignedTypes::class)
    fun fetchUnsignedIntArray(
        key: String,
        defValue: UIntArray? = null
    ): UIntArray? {
        return null
    }

    /**
     * Method to remove locally a [UIntArray] value by its key
     *
     * @param key Is the key of the unsigned int array to remove
     */
    fun removeUnsignedIntArray(
        key: String
    ) {
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
     * Method to fetch locally a [Float] value
     *
     * @param key Is the key of the float to fetch
     * @param defValue Is the value to return if the searched one does not exist
     */
    fun fetchFloat(
        key: String,
        defValue: Float? = null
    ): Float? {
        return prefsWorker.fetch(
            key = key,
            defValue = defValue
        )?.toFloatOrNull()
    }

    /**
     * Method to remove locally a [Float] value by its key
     *
     * @param key Is the key of the float to remove
     */
    fun removeFloat(
        key: String
    ) {
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
    }

    /**
     * Method to fetch locally a [FloatArray] value
     *
     * @param key Is the key of the float array to fetch
     * @param defValue Is the value to return if the searched one does not exist
     */
    fun fetchFloatArray(
        key: String,
        defValue: FloatArray? = null
    ): FloatArray? {
        return null
    }

    /**
     * Method to remove locally a [FloatArray] value by its key
     *
     * @param key Is the key of the float array to remove
     */
    fun removeFloatArray(
        key: String
    ) {
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
     * Method to fetch locally a [Double] value
     *
     * @param key Is the key of the double to fetch
     * @param defValue Is the value to return if the searched one does not exist
     */
    fun fetchDouble(
        key: String,
        defValue: Double? = null
    ): Double? {
        return prefsWorker.fetch(
            key = key,
            defValue = defValue
        )?.toDoubleOrNull()
    }

    /**
     * Method to remove locally a [Double] value by its key
     *
     * @param key Is the key of the double to remove
     */
    fun removeDouble(
        key: String
    ) {
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
    }

    /**
     * Method to fetch locally a [DoubleArray] value
     *
     * @param key Is the key of the double array to fetch
     * @param defValue Is the value to return if the searched one does not exist
     */
    fun fetchDoubleArray(
        key: String,
        defValue: DoubleArray? = null
    ): DoubleArray? {
        return null
    }

    /**
     * Method to remove locally a [DoubleArray] value by its key
     *
     * @param key Is the key of the double array to remove
     */
    fun removeDoubleArray(
        key: String
    ) {
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
     * Method to fetch locally a [Long] value
     *
     * @param key Is the key of the long to fetch
     * @param defValue Is the value to return if the searched one does not exist
     */
    fun fetchLong(
        key: String,
        defValue: Long? = null
    ): Long? {
        return prefsWorker.fetch(
            key = key,
            defValue = defValue
        )?.toLongOrNull()
    }

    /**
     * Method to remove locally a [Long] value by its key
     *
     * @param key Is the key of the long to remove
     */
    fun removeLong(
        key: String
    ) {
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
    }

    /**
     * Method to fetch locally a [LongArray] value
     *
     * @param key Is the key of the long array to fetch
     * @param defValue Is the value to return if the searched one does not exist
     */
    fun fetchLongArray(
        key: String,
        defValue: LongArray? = null
    ): LongArray? {
        return null
    }

    /**
     * Method to remove locally a [LongArray] value by its key
     *
     * @param key Is the key of the long array to remove
     */
    fun removeLongArray(
        key: String
    ) {
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
     * Method to fetch locally a [ULong] value
     *
     * @param key Is the key of the unsigned long to fetch
     * @param defValue Is the value to return if the searched one does not exist
     */
    fun fetchUnsignedLong(
        key: String,
        defValue: ULong? = null
    ): ULong? {
        return prefsWorker.fetch(
            key = key,
            defValue = defValue
        )?.toULongOrNull()
    }

    /**
     * Method to remove locally a [ULong] value by its key
     *
     * @param key Is the key of the unsigned long to remove
     */
    fun removeUnsignedLong(
        key: String
    ) {
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
    }

    /**
     * Method to fetch locally a [ULongArray] value
     *
     * @param key Is the key of the unsigned long array to fetch
     * @param defValue Is the value to return if the searched one does not exist
     */
    @OptIn(ExperimentalUnsignedTypes::class)
    fun fetchUnsignedLongArray(
        key: String,
        defValue: ULongArray? = null
    ): ULongArray? {
        return null
    }

    /**
     * Method to remove locally a [ULongArray] value by its key
     *
     * @param key Is the key of the unsigned long array to remove
     */
    fun removeUnsignedLongArray(
        key: String
    ) {
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
     * Method to fetch locally a [String] value
     *
     * @param key Is the key of the string to fetch
     * @param defValue Is the value to return if the searched one does not exist
     */
    fun fetchString(
        key: String,
        defValue: String? = null
    ): String? {
        return prefsWorker.fetch(
            key = key,
            defValue = defValue
        )
    }

    /**
     * Method to remove locally a [String] value by its key
     *
     * @param key Is the key of the string to remove
     */
    fun removeString(
        key: String
    ) {
    }

    /**
     * Method to remove locally a value by its key
     *
     * @param key Is the key of the value to remove
     */
    fun removeValue(
        key: String
    ) {
    }

    /**
     * Method to clear the all preferences specified by the path
     */
    fun clearAll() {}

}