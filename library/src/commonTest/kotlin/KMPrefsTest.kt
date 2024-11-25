import kotlin.test.*

class KMPrefsTest {

    private lateinit var prefs: KMPrefs

    @BeforeTest
    fun setUp() {
        prefs = KMPrefs("prova")
    }

    @AfterTest
    fun tearDown() {
        prefs.clearAll()
    }

    // Test storeBoolean e fetchBoolean
    @Test
    fun testStoreAndFetchBoolean() {
        val key = "booleanKey"
        val value = true

        prefs.storeBoolean(key, value)
        assertEquals(value, prefs.fetchBoolean(key))

        // Test valore di default
        assertEquals(false, prefs.fetchBoolean("nonExistingKey"))
    }

    // Test removeBoolean
    @Test
    fun testRemoveBoolean() {
        val key = "booleanKey"
        prefs.storeBoolean(key, true)
        prefs.removeBoolean(key)

        assertEquals(false, prefs.fetchBoolean(key)) // Valore di default
    }

    // Test storeByte e fetchByte
    @Test
    fun testStoreAndFetchByte() {
        val key = "byteKey"
        val value: Byte = 42

        prefs.storeByte(key, value)
        assertEquals(value, prefs.fetchByte(key))

        // Test valore di default
        assertEquals(Byte.MIN_VALUE, prefs.fetchByte("nonExistingKey"))
    }

    // Test removeByte
    @Test
    fun testRemoveByte() {
        val key = "byteKey"
        prefs.storeByte(key, 42)
        prefs.removeByte(key)

        assertEquals(Byte.MIN_VALUE, prefs.fetchByte(key)) // Valore di default
    }

    // Test storeByteArray e fetchByteArray
    @Test
    fun testStoreAndFetchByteArray() {
        val key = "byteArrayKey"
        val value = byteArrayOf(1, 2, 3)

        prefs.storeByteArray(key, value)
        assertContentEquals(value, prefs.fetchByteArray(key))

        // Test valore di default
        assertNull(prefs.fetchByteArray("nonExistingKey"))
    }

    // Test removeByteArray
    @Test
    fun testRemoveByteArray() {
        val key = "byteArrayKey"
        prefs.storeByteArray(key, byteArrayOf(1, 2, 3))
        prefs.removeByteArray(key)

        assertNull(prefs.fetchByteArray(key))
    }

    // Test storeUnsignedByte e fetchUnsignedByte
    @Test
    fun testStoreAndFetchUnsignedByte() {
        val key = "unsignedByteKey"
        val value: UByte = 100u

        prefs.storeUnsignedByte(key, value)
        assertEquals(value, prefs.fetchUnsignedByte(key))

        // Test valore di default
        assertEquals(UByte.MIN_VALUE, prefs.fetchUnsignedByte("nonExistingKey"))
    }

    // Test removeUnsignedByte
    @Test
    fun testRemoveUnsignedByte() {
        val key = "unsignedByteKey"
        prefs.storeUnsignedByte(key, 100u)
        prefs.removeUnsignedByte(key)

        assertEquals(UByte.MIN_VALUE, prefs.fetchUnsignedByte(key))
    }

    // Test storeString e fetchString
    @Test
    fun testStoreAndFetchString() {
        val key = "stringKey"
        val value = "Hello, World!"

        prefs.storeString(key, value)
        assertEquals(value, prefs.fetchString(key))

        // Test valore di default
        assertNull(prefs.fetchString("nonExistingKey"))
    }

    // Test removeString
    @Test
    fun testRemoveString() {
        val key = "stringKey"
        prefs.storeString(key, "Hello")
        prefs.removeString(key)

        assertNull(prefs.fetchString(key))
    }

    // Test clearAll
    @Test
    fun testClearAll() {
        prefs.storeBoolean("booleanKey", true)
        prefs.storeString("stringKey", "Hello")

        prefs.clearAll()

        assertEquals(false, prefs.fetchBoolean("booleanKey")) // Default
        assertNull(prefs.fetchString("stringKey")) // Default
    }
}
