package com.tecknobit.kmprefs

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
internal expect class PrefsWorker(
    path: String
) {

    fun store(
        key: String,
        value: Any?
    )

    fun <T> fetch(
        key: String,
        defValue: T?
    ) : String?

    fun remove(
        key: String
    )

    fun clearAll()

}