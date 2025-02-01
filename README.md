# KMPrefs

![Maven Central](https://img.shields.io/maven-central/v/io.github.n7ghtm4r3/KMPrefs.svg?label=Maven%20Central)

![Static Badge](https://img.shields.io/badge/android-4280511051?link=https%3A%2F%2Fimg.shields.io%2Fbadge%2Fandroid-4280511051)
![Static Badge](https://img.shields.io/badge/ios-445E91?link=https%3A%2F%2Fimg.shields.io%2Fbadge%2Fandroid-4280511051)
![Static Badge](https://img.shields.io/badge/desktop-006874?link=https%3A%2F%2Fimg.shields.io%2Fbadge%2Fandroid-4280511051)
![Static Badge](https://img.shields.io/badge/wasmjs-834C74?link=https%3A%2F%2Fimg.shields.io%2Fbadge%2Fandroid-4280511051)

**v1.0.1**

The Kotlin Multiplatform Pref(erence)s system allows you to store, retrieve, and remove data locally on each platform.
It leverages the native mechanisms provided by each platform.

## Implementation

### Version catalog

- `libs.version.toml`

```gradle
[versions]
kmprefs = "1.0.1"

[libraries]
kmprefs = { module = "io.github.n7ghtm4r3:KMPrefs", version.ref = "kmprefs" }
```

- `build.gradle.kts`

```gradle
dependencies {
    implementation(libs.kmprefs)
}
```

### Gradle (Kotlin)

```gradle
dependencies {
    implementation("io.github.n7ghtm4r3:KMPrefs:1.0.1")
}
```

### Gradle (Groovy)

```gradle
dependencies {
    implementation 'io.github.n7ghtm4r3:KMPrefs:1.0.1'
}
```

## Core functionality

### Supported types

| **Type**         | **Description**                            |
|------------------|--------------------------------------------|
| `Boolean`        | Boolean type (true/false)                  |
| `BooleanArray`   | Array of `Boolean`                         |
| `Byte`           | 8-bit signed integer                       |
| `ByteArray`      | Array of `Byte`                            |
| `UByte`          | 8-bit unsigned integer                     |
| `UByteArray`     | Array of `UByte`                           |
| `Short`          | 16-bit signed integer                      |
| `ShortArray`     | Array of `Short`                           |
| `UShort`         | 16-bit unsigned integer                    |
| `UShortArray`    | Array of `UShort`                          |
| `Int`            | 32-bit signed integer                      |
| `IntArray`       | Array of `Int`                             |
| `UInt`           | 32-bit unsigned integer                    |
| `UIntArray`      | Array of `UInt`                            |
| `Float`          | 32-bit floating-point number               |
| `FloatArray`     | Array of `Float`                           |
| `Double`         | 64-bit floating-point number               |
| `DoubleArray`    | Array of `Double`                          |
| `Long`           | 64-bit signed integer                      |
| `LongArray`      | Array of `Long`                            |
| `ULong`          | 64-bit unsigned integer                    |
| `ULongArray`     | Array of `ULong`                           |
| `String`         | String of characters                       |

### Store values

Same procedure for all the types

```kotlin
val kmPrefs = KMPrefs("your_storage_path") // create an instance

// for example store a Double value
kmPrefs.storeDouble(
    key = "constant", // the key of the double value to store
    value = 3.14159265359 // the double value to store
)
```

### Retrieve values

Same procedure for all the types

```kotlin
// for example retrive a Double value
val constant = kmPrefs.retrieveDouble(
    key = "constant",
    defValue = 1.6180339887 // a default value to use if the searched one is not stored yet
)
```

### Remove values

```kotlin
// same method for all the types
kmPrefs.removeValue(
    key = "constant"
)  
```

### Check whether a key has been stored

```kotlin
// same method for all the types
kmPrefs.hasKey(
    key = "your_key"
)
```

### Check whether a value matches with a specified one

```kotlin
// same method for all the types
kmPrefs.valueMatchesTo(
    key = "your_key",
    matcher = // the matcher value such "Hello World!", 22, doubleArrayOf(8, 1) etc...
)
```

## Authors

- [@N7ghtm4r3](https://www.github.com/N7ghtm4r3)

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
