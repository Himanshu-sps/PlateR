package com.plater.android.core.datastore

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Decorates [DataStoreManager] so that callers can persist sensitive strings
 * (tokens, sessions, secrets) without touching crypto primitives.
 */
@Singleton
class EncryptedDataStoreManager @Inject constructor(
    private val dataStoreManager: DataStoreManager
) {

    fun readSecureString(key: String): Flow<String?> =
        dataStoreManager.readString(key, defaultValue = EMPTY)
            .map(::decryptOrNull)

    suspend fun writeSecureString(key: String, value: String?) {
        val normalized = value?.takeIf { it.isNotBlank() }
        val payload = normalized?.let(EncryptionManager::encrypt) ?: EMPTY
        dataStoreManager.writeString(key, payload)
    }

    suspend fun clearSecureKey(key: String) {
        dataStoreManager.writeString(key, EMPTY)
    }

    private fun decryptOrNull(encryptedValue: String): String? =
        encryptedValue.takeIf { it.isNotBlank() }?.let {
            runCatching { EncryptionManager.decrypt(it) }.getOrNull()
        }

    private companion object {
        const val EMPTY = ""
    }
}

