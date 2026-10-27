package dev.template.kmpbase.core.security

import java.util.prefs.Preferences

class DesktopSecureStorage : SecureStorage {
    private val prefs = Preferences.userRoot().node("kmpbase_secure")

    override fun getString(key: String, defaultValue: String): String = prefs.get(key, defaultValue)
    override fun putString(key: String, value: String)               { prefs.put(key, value) }
    override fun remove(key: String)                                 { prefs.remove(key) }
    override fun clear()                                             { prefs.clear() }
    override fun hasKey(key: String)                                 = prefs.get(key, null) != null
}
