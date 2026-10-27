package dev.template.kmpbase.core.security

/**
 * Key/value storage for secrets (auth token, endpoint overrides).
 *
 * Each platform provides its own implementation (`AndroidSecureStorage`, `DesktopSecureStorage`,
 * `IosSecureStorage`); the platform entry point registers it in Koin, so common code only ever
 * sees this interface and tests can bind a fake.
 */
interface SecureStorage {
    fun getString(key: String, defaultValue: String = ""): String
    fun putString(key: String, value: String)
    fun remove(key: String)
    fun clear()
    fun hasKey(key: String): Boolean
}
