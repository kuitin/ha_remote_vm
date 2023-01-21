package com.ha_remote.clientvm.ui.main.cryptage
import java.io.File
import android.os.Environment

internal class SaveDatas (ExternalCacheDir: File) {
    private val SAMPLE_ALIAS = "HAQUENTINQQ"
    private var encryptor: EnCryptor? = null
    private var decryptor: DeCryptor? = null
    private var externalCacheDir: File =ExternalCacheDir

    init
    {
        encryptor = EnCryptor()
        decryptor = DeCryptor()
    }

    fun createFile(sensorName: String)
    {
//        val externalStorageDirectory = Environment.getExternalStorageDirectory()
        var success = true
        val fileName = (encryptor?.encryptText(SAMPLE_ALIAS, sensorName) ?: byteArrayOf()).toString()
        val externalStorageDirectory = File( externalCacheDir,  fileName + ".txt")
        success = externalStorageDirectory.createNewFile()
        var temp = externalStorageDirectory.toURI()

    }
}