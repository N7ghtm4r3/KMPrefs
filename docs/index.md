# Overview

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
