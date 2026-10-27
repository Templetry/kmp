package dev.template.kmpbase.core.security

import platform.Foundation.NSUserDefaults

class IosSecureStorage : SecureStorage {
    private val defaults = NSUserDefaults.standardUserDefaults

    override fun getString(key: String, defaultValue: String) =
        defaults.stringForKey(key) ?: defaultValue
    override fun putString(key: String, value: String) =
        defaults.setObject(value, forKey = key)
    override fun remove(key: String)  = defaults.removeObjectForKey(key)
    override fun clear()              = defaults.dictionaryRepresentation().keys.forEach { remove(it as String) }
    override fun hasKey(key: String)  = defaults.objectForKey(key) != null
}
