package dev.template.kmpbase.core.security

import java.util.prefs.Preferences

actual class SecureStorage {
    private val prefs = Preferences.userRoot().node("kmpbase_secure")

    actual fun getString(key: String, defaultValue: String) = prefs.get(key, defaultValue)
    actual fun putString(key: String, value: String)        { prefs.put(key, value) }
    actual fun remove(key: String)                          { prefs.remove(key) }
    actual fun clear()                                      { prefs.clear() }
    actual fun hasKey(key: String)                          = prefs.get(key, null) != null
}
