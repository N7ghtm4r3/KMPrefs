package com.tecknobit.kmprefs

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
internal actual class PrefsWorker actual constructor(
    path: String
) {
    actual fun store(key: String, value: Any?) {
    }

    actual fun <T> fetch(key: String, defValue: T?): String? {
        TODO("Not yet implemented")
    }

    actual fun remove(key: String) {
    }

    actual fun clearAll() {
    }


}