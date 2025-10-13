# KMPrefs

![Maven Central](https://img.shields.io/maven-central/v/io.github.n7ghtm4r3/kmprefs.svg?label=Maven%20Central)

![Static Badge](https://img.shields.io/badge/android-4280511051?link=https%3A%2F%2Fimg.shields.io%2Fbadge%2Fandroid-4280511051)
![Static Badge](https://img.shields.io/badge/apple-445E91?link=https%3A%2F%2Fimg.shields.io%2Fbadge%2Fandroid-4280511051)
![Static Badge](https://img.shields.io/badge/desktop-006874?link=https%3A%2F%2Fimg.shields.io%2Fbadge%2Fandroid-4280511051)
![Static Badge](https://img.shields.io/badge/wasmjs-834C74?link=https%3A%2F%2Fimg.shields.io%2Fbadge%2Fandroid-4280511051)

**v1.1.0**

**Kotlin Multiplatform Pref(erence)s** system allows you to locally store, retrieve, and remove data on each platform,
leveraging the native APIs provided by each platform:

- The `Android` target leverages the [SharedPreferences](https://developer.android.com/reference/android/content/SharedPreferences) APIs
- The `iOS` and `macOS` targets leverage the [UserDefaults](https://developer.apple.com/documentation/foundation/userdefaults) APIs
- The `JVM` target leverages the [Preferences](https://docs.oracle.com/javase/8/docs/api/java/util/prefs/Preferences.html) APIs
- The `Web` target leverages the [LocalStorage](https://developer.mozilla.org/en-US/docs/Web/API/Window/localStorage) APIs

## Implementation

### Gradle short

```groovy
dependencies {
    implementation 'io.github.n7ghtm4r3:kmprefs:1.1.0'
}
```

### Gradle (Kotlin)

```kotlin
dependencies {
    implementation("io.github.n7ghtm4r3:kmprefs:1.1.0")
}
```

### Gradle (version catalog)

#### libs.versions.toml

```toml
[versions]
kmprefs = "1.1.0"

[libraries]
kmprefs = { module = "io.github.n7ghtm4r3:kmprefs", version.ref = "kmprefs" }
```

#### build.gradle.kts

```kotlin
dependencies {
    implementation(libs.kmprefs)
}
```

## Usage

### Supported types

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

### Storing values

#### Primitives

```kotlin
val kmPrefs = KMPrefs("your_storage_path") // create an instance

kmPrefs.store(
    key = "constant", // the key of the value to store
    value = 3.14159265359 // a primitive value to store
)
```

#### Custom objects

Under the hood `KMPrefs` works with the [kotlinx-serialization](https://github.com/Kotlin/kotlinx.serialization) library
so it is required to import both the library and the plugin to correctly store and retrieve custom objects

<h6>Create the @Serializable object</h6>

```kotlin
@Serializable // required
data class Car(
    val plate: String,
    val hp: Int
)
```

<h6>Store the object</h6>

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

#### Sensitive data

To safeguard sensitive data (such as token, ids, etc...) you can do as follows to encrypt values before their storage:

```kotlin
val kmPrefs = KMPrefs("your_storage_path") // create an instance

kmPrefs.store(
    key = "constant", // the key of the value to store
    value = 3.14159265359, // a primitive value to store,
    isSensitive = true // the value will be stored encrypted 
)
```

### Retrieving values

#### Primitives

```kotlin
val constant: Double? = kmPrefs.retrieve(
    key = "constant",
    defValue = 1.6180339887 // a default value to use whether the searched one is not stored yet
)
```

#### Custom objects

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

#### Sensitive data

To correctly retrieve and use a sensitive data previously stored you can follow the below guide

<h6>Project does not target Web platform</h6>

If your project does not target the `Web` platform you can normally use the `retrieve` method to retrieve the sensitive 
data and then using it:

```kotlin
val constant: Double? = kmPrefs.retrieve(
    key = "constant",
    defValue = 1.6180339887, // a default value to use whether the searched one is not stored yet
    isSensitive = true // the value will be retrieved decrypted 
)
```

<h6>Project targets Web platform</h6>

Otherwise, if your project targets the `Web` platform you have to use the `consumeRetrieval` method to retrieve the 
sensitive data and then consuming it:

```kotlin
kmPrefs.consumeRetrieval<Double>(
    key = "constant",
    defValue = 1.6180339887, // a default value to use whether the searched one is not stored yet
    isSensitive = true, // the value will be retrieved decrypted 
    consume = { retrievedValue ->
        println(retrievedValue) // decrypted value locally retrieved
    }
)
```

### Removing values

#### Single preference

With the `removeValue` method you can remove a preference previously stored, it has no effect if the specified key is not
used by any preference

```kotlin
kmPrefs.removeValue(
    key = "constant"
)  
```

#### All preferences

With the `clearAll` method you can remove all the preferences currently stored by the library:

```kotlin
kmPrefs.clearAll()  
```

### Checking key availability

With the `hasKey` method you can check whether a key has been previously used to store a preference or whether a
specific preference is currently stored:

```kotlin
kmPrefs.hasKey(
    key = "your_key"
)
```

## Documentation

Check out the library documentation [here!](https://n7ghtm4r3.github.io/KMPrefs/)

## Support

If you need help using the library or encounter any problems or bugs, please contact us via the
following links:

- Support via <a href="mailto:infotecknobitcompany@gmail.com">email</a>
- Support via <a href="https://github.com/N7ghtm4r3/KMPrefs/issues/new">GitHub</a>

Thank you for your help!

## Donations

If you want support project and developer

| Crypto                                                                                              | Address                                          | Network  |
|-----------------------------------------------------------------------------------------------------|--------------------------------------------------|----------|
| ![](https://img.shields.io/badge/Bitcoin-000000?style=for-the-badge&logo=bitcoin&logoColor=white)   | **3H3jyCzcRmnxroHthuXh22GXXSmizin2yp**           | Bitcoin  |
| ![](https://img.shields.io/badge/Ethereum-3C3C3D?style=for-the-badge&logo=Ethereum&logoColor=white) | **0x1b45bc41efeb3ed655b078f95086f25fc83345c4**   | Ethereum |
| ![](https://img.shields.io/badge/Solana-000?style=for-the-badge&logo=Solana&logoColor=9945FF)       | **AtPjUnxYFHw3a6Si9HinQtyPTqsdbfdKX3dJ1xiDjbrL** | Solana   |

If you want support project and developer
with <a href="https://www.paypal.com/donate/?hosted_button_id=5QMN5UQH7LDT4">PayPal</a>

Copyright © 2025 Tecknobit