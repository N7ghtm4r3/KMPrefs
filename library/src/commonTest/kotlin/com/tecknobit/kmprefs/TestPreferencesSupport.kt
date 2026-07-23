package com.tecknobit.kmprefs

private var pathSequence = 0L

internal fun uniquePreferencesPath(
    feature: String
): String {
    pathSequence += 1
    return "com.tecknobit.kmprefs.tests.$feature.$pathSequence"
}

internal inline fun withCleanPreferences(
    feature: String,
    test: (KMPrefs) -> Unit
) {
    val preferences = KMPrefs(
        path = uniquePreferencesPath(feature)
    )
    try {
        preferences.clearAll()
        test(preferences)
    } finally {
        preferences.clearAll()
    }
}
