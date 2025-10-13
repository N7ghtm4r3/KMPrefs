## Supported types

| **Type**       | **Description**              |
|----------------|------------------------------|
| `Boolean`      | Boolean type (true/false)    |
| `BooleanArray` | Array of `Boolean`           |
| `Byte`         | 8-bit signed integer         |
| `ByteArray`    | Array of `Byte`              |
| `UByte`        | 8-bit unsigned integer       |
| `UByteArray`   | Array of `UByte`             |
| `Short`        | 16-bit signed integer        |
| `ShortArray`   | Array of `Short`             |
| `UShort`       | 16-bit unsigned integer      |
| `UShortArray`  | Array of `UShort`            |
| `Int`          | 32-bit signed integer        |
| `IntArray`     | Array of `Int`               |
| `UInt`         | 32-bit unsigned integer      |
| `UIntArray`    | Array of `UInt`              |
| `Float`        | 32-bit floating-point number |
| `FloatArray`   | Array of `Float`             |
| `Double`       | 64-bit floating-point number |
| `DoubleArray`  | Array of `Double`            |
| `Long`         | 64-bit signed integer        |
| `LongArray`    | Array of `Long`              |
| `ULong`        | 64-bit unsigned integer      |
| `ULongArray`   | Array of `ULong`             |
| `String`       | String of characters         |
| `Serializable` | Custom serializable objects  |
| `Enum`         | Entry of any `Enum`          |

## Storing values

### Primitives

!!! Info

    For _Primitives_ is meaning all the types natively provided such **Int**, **String**, **Enum** and all the **primitive arrays** 

```kotlin
val kmPrefs = KMPrefs("your_storage_path") // create an instance

kmPrefs.store(
    key = "constant", // the key of the value to store
    value = 3.14159265359 // a primitive value to store
)
```

### Custom objects

Under the hood `KMPrefs` works with the [kotlinx-serialization](https://github.com/Kotlin/kotlinx.serialization) library
so it is required to import both the library and the plugin to correctly store and retrieve custom objects

#### Create the @Serializable object

```kotlin
@Serializable // required
data class Car(
    val plate: String,
    val hp: Int
)
```

#### Store the object

```kotlin
val kmPrefs = KMPrefs("your_storage_path") // create an instance

// create the instance to store
val carToStore = Car(
    plate = "AA000AA",
    hp = 450
)

// store the created instance 
kmPrefs.store(
    key = "your_key",
    value = carToStore
) 
```

### Sensitive data

To safeguard sensitive data (such as token, ids, etc...) you can do as follows to encrypt values before their storage:

```kotlin
val kmPrefs = KMPrefs("your_storage_path") // create an instance

kmPrefs.store(
    key = "constant", // the key of the value to store
    value = 3.14159265359, // a primitive value to store,
    isSensitive = true // the value will be stored encrypted 
)
```

## Retrieving values

### Primitives

```kotlin
val constant: Double? = kmPrefs.retrieve(
    key = "constant",
    defValue = 1.6180339887 // a default value to use whether the searched one is not stored yet
)
```

### Custom objects

```kotlin
// retrieve the car
val carToStore: Car = kmPrefs.retrieve(
    key = "your_key",
    deserializer = ,// custom deserializer to use during the retrieve whether the type is not explicit
    defValue = Car(
        plate = "not_found",
        hp = 0
    ) // a default value to use whether the searched one is not stored yet
)
```

### Sensitive data

To correctly retrieve and use a sensitive data previously stored you can follow the below guide

#### Project does not target Web platform

If your project does not target the `Web` platform you can normally use the `retrieve` method to retrieve the sensitive
data and then using it:

```kotlin
val constant: Double? = kmPrefs.retrieve(
    key = "constant",
    defValue = 1.6180339887, // a default value to use whether the searched one is not stored yet
    isSensitive = true // the value will be retrieved decrypted 
)
```

#### Project targets Web platform

Otherwise, if your project targets the `Web` platform you have to use the `consumeRetrieval` method to retrieve the
sensitive data and then consuming it: 

```kotlin
kmPrefs.consumeRetrieval<Double>(
    key = "constant",
    defValue = 1.6180339887, // a default value to use whether the searched one is not stored yet
    isSensitive = isSensitive, // the value will be retrieved decrypted 
    consume = { retrievedValue ->
        println(retrievedValue) // decrypted value locally retrieved
    }
)
```

!!! Warning

    Normally using the `retrieve` method when the data to retrieve is a **sensitive data** on the `Web` platform, will 
    cause a crash of the web app due wrong deserialization of the encrypted data

## Removing values

### Single preference

With the `removeValue` method you can remove a preference previously stored, it has no effect if the specified key is not
used by any preference

```kotlin
kmPrefs.removeValue(
    key = "constant"
)  
```

### All preferences

With the `clearAll` method you can remove all the preferences currently stored by the library:

```kotlin
kmPrefs.clearAll()  
```

## Checking key availability

With the `hasKey` method you can check whether a key has been previously used to store a preference or whether a 
specific preference is currently stored: 

```kotlin
kmPrefs.hasKey(
    key = "your_key"
)
```