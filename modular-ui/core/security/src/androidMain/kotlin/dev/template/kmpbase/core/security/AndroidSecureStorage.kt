package dev.template.kmpbase.core.security

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

lateinit var appContext: Context

class AndroidSecureStorage(private val context: Context) : SecureStorage {
    private val prefs by lazy {
        val master = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()
        EncryptedSharedPreferences.create(
            context, "secure_prefs", master,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
        )
    }

    override fun getString(key: String, defaultValue: String) =
        prefs.getString(key, defaultValue) ?: defaultValue
    override fun putString(key: String, value: String) =
        prefs.edit().putString(key, value).apply()
    override fun remove(key: String) = prefs.edit().remove(key).apply()
    override fun clear()             = prefs.edit().clear().apply()
    override fun hasKey(key: String) = prefs.contains(key)
}
