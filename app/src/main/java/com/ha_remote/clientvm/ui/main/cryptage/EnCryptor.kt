package com.ha_remote.clientvm.ui.main.cryptage

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey


/**
 * ______        _____                  _
 * |  ____|      / ____|                | |
 * | |__   _ __ | |     _ __ _   _ _ __ | |_ ___  _ __
 * |  __| | '_ \| |    | '__| | | | '_ \| __/ _ \| '__|
 * | |____| | | | |____| |  | |_| | |_) | || (_) | |
 * |______|_| |_|\_____|_|   \__, | .__/ \__\___/|_|
 * __/ | |
 * |___/|_|
 */
internal class EnCryptor {
    lateinit var encryption: ByteArray
        private set
    lateinit var iv: ByteArray
        private set


    fun encryptText(alias: String, textToEncrypt: String): ByteArray {
        val cipher: Cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(alias))
        iv = cipher.getIV()
        return cipher.doFinal(textToEncrypt.toByteArray(charset("UTF-8"))).also {
            encryption = it
        }
    }


    private fun getSecretKey(alias: String): SecretKey {
        val keyGenerator: KeyGenerator = KeyGenerator
            .getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEY_STORE)
        keyGenerator.init(
            KeyGenParameterSpec.Builder(
                alias,
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            )
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .build()
        )
        return keyGenerator.generateKey()
    }

    companion object {
        private const val TRANSFORMATION = "AES/GCM/NoPadding"
        private const val ANDROID_KEY_STORE = "AndroidKeyStore"
    }

    @JvmName("getEncryption1")
    fun getEncryption(): ByteArray? {
        return encryption
    }

    @JvmName("getIv1")
    fun getIv(): ByteArray? {
        return iv
    }
}