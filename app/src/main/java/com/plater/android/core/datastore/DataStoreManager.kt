package com.plater.android.core.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private const val DATA_STORE_NAME = "app_preferences"
private val Context.mainPreferencesDataStore by preferencesDataStore(name = DATA_STORE_NAME)

/**
 * Central manager that wraps the app's main DataStore instance and exposes helper
 * methods for reading/writing primitive types without leaking key creation logic.
 */
@Singleton
class DataStoreManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val dataStore by lazy { context.mainPreferencesDataStore }

    fun readBoolean(key: String, defaultValue: Boolean = false): Flow<Boolean> =
        dataStore.data.map { preferences ->
            preferences[booleanPreferencesKey(key)] ?: defaultValue
        }

    suspend fun writeBoolean(key: String, value: Boolean) {
        dataStore.edit { preferences ->
            preferences[booleanPreferencesKey(key)] = value
        }
    }

    fun readInt(key: String, defaultValue: Int = 0): Flow<Int> =
        dataStore.data.map { preferences ->
            preferences[intPreferencesKey(key)] ?: defaultValue
        }

    suspend fun writeInt(key: String, value: Int) {
        dataStore.edit { preferences ->
            preferences[intPreferencesKey(key)] = value
        }
    }

    fun readLong(key: String, defaultValue: Long = 0L): Flow<Long> =
        dataStore.data.map { preferences ->
            preferences[longPreferencesKey(key)] ?: defaultValue
        }

    suspend fun writeLong(key: String, value: Long) {
        dataStore.edit { preferences ->
            preferences[longPreferencesKey(key)] = value
        }
    }

    fun readDouble(key: String, defaultValue: Double = 0.0): Flow<Double> =
        dataStore.data.map { preferences ->
            preferences[doublePreferencesKey(key)] ?: defaultValue
        }

    suspend fun writeDouble(key: String, value: Double) {
        dataStore.edit { preferences ->
            preferences[doublePreferencesKey(key)] = value
        }
    }

    fun readFloat(key: String, defaultValue: Float = 0f): Flow<Float> =
        dataStore.data.map { preferences ->
            preferences[floatPreferencesKey(key)] ?: defaultValue
        }

    suspend fun writeFloat(key: String, value: Float) {
        dataStore.edit { preferences ->
            preferences[floatPreferencesKey(key)] = value
        }
    }

    fun readString(key: String, defaultValue: String = ""): Flow<String> =
        dataStore.data.map { preferences ->
            preferences[stringPreferencesKey(key)] ?: defaultValue
        }

    suspend fun writeString(key: String, value: String) {
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey(key)] = value
        }
    }

    fun readStringSet(key: String, defaultValue: Set<String> = emptySet()): Flow<Set<String>> =
        dataStore.data.map { preferences ->
            preferences[stringSetPreferencesKey(key)] ?: defaultValue
        }

    suspend fun writeStringSet(key: String, value: Set<String>) {
        dataStore.edit { preferences ->
            preferences[stringSetPreferencesKey(key)] = value
        }
    }

    suspend fun clear() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}


