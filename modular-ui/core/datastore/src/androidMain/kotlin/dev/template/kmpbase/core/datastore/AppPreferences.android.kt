package dev.template.kmpbase.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dev.template.kmpbase.core.security.appContext

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_prefs")

actual fun createDataStore(): DataStore<Preferences> = appContext.dataStore
