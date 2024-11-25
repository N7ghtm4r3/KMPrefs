package com.tecknobit.kmprefs

/**
 * The **KMPrefs** class helps to manage the preferences storing the data locally
 *
 * @param path Is the path where store the data
 *
 * @author N7ghtm4r3 - Tecknobit
 */
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class KMPrefs(
    path: String
) {

    /**
     * Method to store locally a [Boolean] value
     *
     * @param key Is the key of the boolean
     * @param value Is the value to store
     */
    fun storeBoolean(
        key: String,
        value: Boolean?
    )

    /**
     * Method to fetch locally a [Boolean] value
     *
     * @param key Is the key of the boolean to fetch
     * @param defValue Is the value to return if the searched one does not exist
     */
    fun fetchBoolean(
        key: String,
        defValue: Boolean? = null
    ): Boolean?

    /**
     * Method to remove locally a [Boolean] value by its key
     *
     * @param key Is the key of the boolean to remove
     */
    fun removeBoolean(
        key: String
    )

    /**
     * Method to store locally a [Byte] value
     *
     * @param key Is the key of the byte
     * @param value Is the value to store
     */
    fun storeByte(
        key: String,
        value: Byte?
    )

    /**
     * Method to fetch locally a [Byte] value
     *
     * @param key Is the key of the byte to fetch
     * @param defValue Is the value to return if the searched one does not exist
     */
    fun fetchByte(
        key: String,
        defValue: Byte? = null
    ): Byte?

    /**
     * Method to remove locally a [Byte] value by its key
     *
     * @param key Is the key of the byte to remove
     */
    fun removeByte(
        key: String
    )

    /**
     * Method to store locally a [UByte] value
     *
     * @param key Is the key of the unsigned byte
     * @param value Is the value to store
     */
    fun storeUnsignedByte(
        key: String,
        value: UByte?
    )

    /**
     * Method to fetch locally a [UByte] value
     *
     * @param key Is the key of the unsigned byte to fetch
     * @param defValue Is the value to return if the searched one does not exist
     */
    fun fetchUnsignedByte(
        key: String,
        defValue: UByte? = null
    ): UByte?

    /**
     * Method to remove locally a [UByte] value by its key
     *
     * @param key Is the key of the unsigned byte to remove
     */
    fun removeUnsignedByte(
        key: String
    )

    /**
     * Method to store locally a [Short] value
     *
     * @param key Is the key of the short
     * @param value Is the value to store
     */
    fun storeShort(
        key: String,
        value: Short?
    )

    /**
     * Method to fetch locally a [Short] value
     *
     * @param key Is the key of the short to fetch
     * @param defValue Is the value to return if the searched one does not exist
     */
    fun fetchShort(
        key: String,
        defValue: Short? = null
    ): Short?

    /**
     * Method to remove locally a [Short] value by its key
     *
     * @param key Is the key of the short to remove
     */
    fun removeShort(
        key: String
    )

    /**
     * Method to store locally a [UShort] value
     *
     * @param key Is the key of the unsigned short
     * @param value Is the value to store
     */
    fun storeUnsignedShort(
        key: String,
        value: UShort?
    )

    /**
     * Method to fetch locally a [UShort] value
     *
     * @param key Is the key of the unsigned short to fetch
     * @param defValue Is the value to return if the searched one does not exist
     */
    fun fetchUnsignedShort(
        key: String,
        defValue: UShort? = null
    ): UShort?

    /**
     * Method to remove locally a [UShort] value by its key
     *
     * @param key Is the key of the unsigned short to remove
     */
    fun removeUnsignedShort(
        key: String
    )

    /**
     * Method to store locally a [Int] value
     *
     * @param key Is the key of the int
     * @param value Is the value to store
     */
    fun storeInt(
        key: String,
        value: Int?
    )

    /**
     * Method to fetch locally a [Int] value
     *
     * @param key Is the key of the int to fetch
     * @param defValue Is the value to return if the searched one does not exist
     */
    fun fetchInt(
        key: String,
        defValue: Int? = null
    ): Int?

    /**
     * Method to remove locally a [Int] value by its key
     *
     * @param key Is the key of the int to remove
     */
    fun removeInt(
        key: String
    )

    /**
     * Method to store locally a [UInt] value
     *
     * @param key Is the key of the unsigned int
     * @param value Is the value to store
     */
    fun storeUnsignedInt(
        key: String,
        value: UInt?
    )

    /**
     * Method to fetch locally a [UInt] value
     *
     * @param key Is the key of the unsigned int to fetch
     * @param defValue Is the value to return if the searched one does not exist
     */
    fun fetchUnsignedInt(
        key: String,
        defValue: UInt? = null
    ): UInt?

    /**
     * Method to remove locally a [UInt] value by its key
     *
     * @param key Is the key of the unsigned int to remove
     */
    fun removeUnsignedInt(
        key: String
    )

    /**
     * Method to store locally a [Float] value
     *
     * @param key Is the key of the float
     * @param value Is the value to store
     */
    fun storeFloat(
        key: String,
        value: Float?
    )

    /**
     * Method to fetch locally a [Float] value
     *
     * @param key Is the key of the float to fetch
     * @param defValue Is the value to return if the searched one does not exist
     */
    fun fetchFloat(
        key: String,
        defValue: Float? = null
    ): Float?

    /**
     * Method to remove locally a [Float] value by its key
     *
     * @param key Is the key of the float to remove
     */
    fun removeFloat(
        key: String
    )

    /**
     * Method to store locally a [Double] value
     *
     * @param key Is the key of the double
     * @param value Is the value to store
     */
    fun storeDouble(
        key: String,
        value: Double?
    )

    /**
     * Method to fetch locally a [Double] value
     *
     * @param key Is the key of the double to fetch
     * @param defValue Is the value to return if the searched one does not exist
     */
    fun fetchDouble(
        key: String,
        defValue: Double? = null
    ): Double?

    /**
     * Method to remove locally a [Double] value by its key
     *
     * @param key Is the key of the double to remove
     */
    fun removeDouble(
        key: String
    )

    /**
     * Method to store locally a [Long] value
     *
     * @param key Is the key of the long
     * @param value Is the value to store
     */
    fun storeLong(
        key: String,
        value: Long?
    )

    /**
     * Method to fetch locally a [Long] value
     *
     * @param key Is the key of the long to fetch
     * @param defValue Is the value to return if the searched one does not exist
     */
    fun fetchLong(
        key: String,
        defValue: Long? = null
    ): Long?

    /**
     * Method to remove locally a [Long] value by its key
     *
     * @param key Is the key of the long to remove
     */
    fun removeLong(
        key: String
    )

    /**
     * Method to store locally a [ULong] value
     *
     * @param key Is the key of the unsigned long
     * @param value Is the value to store
     */
    fun storeUnsignedLong(
        key: String,
        value: ULong?
    )

    /**
     * Method to fetch locally a [ULong] value
     *
     * @param key Is the key of the unsigned long to fetch
     * @param defValue Is the value to return if the searched one does not exist
     */
    fun fetchUnsignedLong(
        key: String,
        defValue: ULong? = null
    ): ULong?

    /**
     * Method to remove locally a [ULong] value by its key
     *
     * @param key Is the key of the unsigned long to remove
     */
    fun removeUnsignedLong(
        key: String
    )

    /**
     * Method to store locally a [String] value
     *
     * @param key Is the key of the string
     * @param value Is the value to store
     */
    fun storeString(
        key: String,
        value: String?
    )

    /**
     * Method to fetch locally a [String] value
     *
     * @param key Is the key of the string to fetch
     * @param defValue Is the value to return if the searched one does not exist
     */
    fun fetchString(
        key: String,
        defValue: String? = null
    ): String?

    /**
     * Method to remove locally a [String] value by its key
     *
     * @param key Is the key of the string to remove
     */
    fun removeString(
        key: String
    )

    /**
     * Method to clear the all preferences specified by the path
     */
    fun clearAll()

}