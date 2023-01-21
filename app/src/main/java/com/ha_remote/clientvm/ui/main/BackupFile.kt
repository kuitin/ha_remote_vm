package com.ha_remote.clientvm.ui.main

import java.security.KeyStore
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

class BackupFile {


    @Throws(Exception::class)
    fun generateKey(password: String): ByteArray? {
        val keyStart = password.toByteArray(charset("UTF-8"))
        val kgen: KeyGenerator = KeyGenerator.getInstance("AES")
        val sr: SecureRandom = SecureRandom.getInstance("SHA1PRNG")
        sr.setSeed(keyStart)
        kgen.init(128, sr)
        val skey: SecretKey = kgen.generateKey()
        return skey.getEncoded()
    }

    @Throws(Exception::class)
    fun encodeFile(key: ByteArray?, fileData: ByteArray?): ByteArray? {
        val skeySpec = SecretKeySpec(key, "AES")
        val cipher: Cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec)
        return cipher.doFinal(fileData)
    }

    @Throws(Exception::class)
    fun decodeFile(key: ByteArray?, fileData: ByteArray?): ByteArray? {
        val skeySpec = SecretKeySpec(key, "AES")
        val cipher: Cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.DECRYPT_MODE, skeySpec)
        return cipher.doFinal(fileData)
    }
}