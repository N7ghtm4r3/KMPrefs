package com.tecknobit.kmprefs

import android.content.Context
import com.tecknobit.equinoxcompose.helpers.utils.AppContext

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
internal actual class PrefsWorker actual constructor(
    path: String
) {

    private val sharedPreferences = AppContext.get().getSharedPreferences(
        path,
        Context.MODE_PRIVATE
    )

    actual fun store(
        key: String,
        value: Any?,
    ) {
        sharedPreferences.edit().putString(key, value.toString()).commit()
    }

    actual fun <T> fetch(
        key: String,
        defValue: T?,
    ): String? {
        return sharedPreferences.getString(key, defValue?.toString())
    }

    actual fun remove(
        key: String
    ) {
        sharedPreferences.edit().remove(key).commit()
    }

    actual fun clearAll() {
        sharedPreferences.edit().clear().commit()
    }

}