package com.plater.android.core.datastore

import android.util.Base64
import androidx.security.crypto.MasterKeys
import java.nio.ByteBuffer
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

/**
 * Centralized helper that hides the AndroidKeyStore plumbing required to encrypt/decrypt
 * sensitive payloads (tokens, sessions, etc.) before they hit disk.
 */
object EncryptionManager {

    private const val ANDROID_KEYSTORE = "AndroidKeyStore"
    private const val TRANSFORMATION = "AES/GCM/NoPadding"
    private const val GCM_TAG_LENGTH_BITS = 128
    private const val IV_LENGTH_BYTES = 12

    private val keyStore: KeyStore by lazy {
        KeyStore.getInstance(ANDROID_KEYSTORE).apply { load(null) }
    }

    private val masterKeyAlias: String by lazy {
        MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
    }

    private fun masterSecretKey(): SecretKey =
        (keyStore.getEntry(masterKeyAlias, null) as? KeyStore.SecretKeyEntry)
            ?.secretKey
            ?: error("Master key entry not found in keystore")

    private fun cipher(mode: Int, iv: ByteArray? = null): Cipher =
        Cipher.getInstance(TRANSFORMATION).apply {
            if (iv == null) {
                init(mode, masterSecretKey())
            } else {
                val params = GCMParameterSpec(GCM_TAG_LENGTH_BITS, iv)
                init(mode, masterSecretKey(), params)
            }
        }

    fun encrypt(input: String): String {
        val encryptionCipher = cipher(Cipher.ENCRYPT_MODE)
        val iv = encryptionCipher.iv
        val encryptedBytes = encryptionCipher.doFinal(input.toByteArray())

        val payload = ByteBuffer.allocate(IV_LENGTH_BYTES + encryptedBytes.size)
            .put(iv)
            .put(encryptedBytes)
            .array()

        return Base64.encodeToString(payload, Base64.NO_WRAP)
    }

    fun decrypt(input: String): String {
        val decoded = Base64.decode(input, Base64.NO_WRAP)
        require(decoded.size > IV_LENGTH_BYTES) { "Encrypted payload missing IV" }

        val buffer = ByteBuffer.wrap(decoded)
        val iv = ByteArray(IV_LENGTH_BYTES).also { buffer.get(it) }
        val cipherBytes = ByteArray(buffer.remaining()).also { buffer.get(it) }

        val decryptionCipher = cipher(Cipher.DECRYPT_MODE, iv)
        val decryptedBytes = decryptionCipher.doFinal(cipherBytes)
        return String(decryptedBytes)
    }
}
