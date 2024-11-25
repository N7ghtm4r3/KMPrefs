package com.tecknobit.kmprefs

import kotlinx.browser.window

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
internal actual class PrefsWorker actual constructor(
    path: String
) {

    private val localStorage = window.localStorage

    actual fun store(
        key: String,
        value: Any?,
    ) {
        localStorage.setItem(
            key = key,
            value = value.toString()
        )
    }

    actual fun <T> fetch(
        key: String,
        defValue: T?,
    ): String? {
        val value = localStorage.getItem(
            key = key
        )
        return if(value == null)
            defValue?.toString()
        else
            value
    }

    actual fun remove(
        key: String
    ) {
        localStorage.removeItem(
            key = key
        )
    }

    actual fun clearAll() {
        localStorage.clear()
    }

}