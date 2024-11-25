package com.tecknobit.kmprefs

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
internal actual class PrefsWorker actual constructor(
    path: String
) {

    private val userDefaults = NSUserDefaults.standardUserDefaults()

    actual fun store(
        key: String,
        value: Any?,
    ) {
        userDefaults.setObject(
            value = value,
            forKey = key
        )
    }

    actual fun <T> fetch(
        key: String,
        defValue: T?,
    ): String? {
        return userDefaults.objectForKey(
            forKey = key
        )
    }

    actual fun remove(
        key: String
    ) {
        return userDefaults.removeObjectForKey(
            key = key
        )
    }

    actual fun clearAll() {
        userDefaults.removePersistentDomainForName(userDefaults.applicationDomain)
    }


}