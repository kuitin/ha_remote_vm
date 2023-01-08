package com.ha_remote.clientvm.ui.main

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pcloud.sdk.*
import com.pcloud.sdk.Call
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import org.json.JSONObject
import java.io.IOException
import java.lang.reflect.Type
import java.security.KeyFactory
import java.security.spec.PKCS8EncodedKeySpec
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import javax.crypto.Cipher
class PCloud {

    val pcloudUser = Key.getpcloudUser()
    val password= Key.getpassword()
    val privateKey =Key.getprivateKey()

    companion object {
        const val TRANSFORMATION = "AES/GCM/NoPadding"
        const val ANDROID_KEY_STORE = "AndroidKeyStore"
        const val ALIAS = "MyApp"
        const val TAG = "KeyStoreManager"
    }

    private val mclient = OkHttpClient()
    val JSON = "application/json; charset=utf-8".toMediaType()



    @RequiresApi(Build.VERSION_CODES.O)
    fun ParseFilesFolders(str:String): List<FileInfo> {
        var jsonObj = JSONObject(str)
        var filesResult = mutableListOf <FileInfo>()
        var filesArray = jsonObj.getJSONObject("metadata").getJSONArray("contents")
        for (i in 0 until filesArray.length()) {
            val fileInfo = filesArray.getJSONObject(i)
            val l = LocalDate.parse(fileInfo.get("modified") as String?, DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH))
            val unix = l.atStartOfDay(ZoneId.systemDefault()).toInstant().epochSecond
            filesResult.add(FileInfo(fileInfo.get("name") as String, unix, fileInfo.get("fileid").toString()))
        }
        return filesResult
    }

    fun DecryptFileContent(fileContent:String):FileData {

        val listType: Type = object : TypeToken<FileData?>() {}.type
        val filesResult: FileData = Gson().fromJson(fileContent, listType)
        return filesResult
    }

    fun Int.to2ByteArray() : Byte = byteArrayOf(toByte(), shr(8).toByte())[0]

    /**
    Get pair of encrypted value and iv
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun decryptString(encryptedString: String): String {
        var result = ""
        return try {

            val strArray = encryptedString.split(",")
            var counter = 0
            val bytesCount = strArray.count()
            var rlist= ByteArray(bytesCount-1)
            while (bytesCount -  1> counter)
            {
                var temp = strArray[counter].toInt()
                var tempbyte = temp.toUByte()
                rlist[counter] = (temp and 0xff).toByte() // temp.to2ByteArray()
                counter += 1
            }

            decryptData(rlist)
        } catch (e: java.lang.Exception) {
            encryptedString
        }
        result
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun decryptData(encryptedData: ByteArray): String {
        return try {

            val privatekeypem: String = privateKey
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("\\n", "")
                .replace("-----END PRIVATE KEY-----", "")
            val privateBytes: ByteArray = Base64.getDecoder().decode(privateKey)
            val keySpec = PKCS8EncodedKeySpec(privateBytes)
            val spec = PKCS8EncodedKeySpec (privatekeypem.encodeToByteArray())
            val keyFactory = KeyFactory.getInstance("RSA")
            val privateKey = keyFactory.generatePrivate(keySpec)
            val cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding")
            cipher.init(Cipher.DECRYPT_MODE, privateKey)
            val decryptedBytes = cipher.doFinal(encryptedData)

            return String(decryptedBytes)

        } catch (e: Exception) {
            encryptedData.toString()
        }
        return ""
    }





    @RequiresApi(Build.VERSION_CODES.O)
    fun GetFilesList(): List<FileInfo>{

        try {
            val request = Request.Builder()
                .url("https://eapi.pcloud.com/listfolder?folderid=0&recursive=0&iconformat=id&getkey=1&getpublicfolderlink=1")
                .get()
                .addHeader("User-Agent", "OkHttp Bot")
                .build()

            var  strResult : String
            mclient.newCall(request).execute().use { response ->
                strResult = response.body!!.string()
            }
            return ParseFilesFolders(strResult)

        }
        catch (e: IOException) {
            println("Response 1 succeeded: ${e.stackTrace}")

        }
        return mutableListOf<FileInfo>()
    }

    fun DownloadFile(fileId: String) : String{
        try {
            var answer  : JSONObject
            var formBody = FormBody.Builder()
                .add("fileid", fileId)
                .build()
            var request = Request.Builder()
                .url("https://eapi.pcloud.com/gettextfile")
                .post(formBody)
                .addHeader("User-Agent", "OkHttp Bot")
                .build()

            var  strResult : String
            mclient.newCall(request).execute().use { response ->
                strResult = response.body!!.string()
            }
            return strResult


        }
        catch (e: IOException) {
            println("Response 1 succeeded: ${e.stackTrace}")

        }
        return ""
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun GetAllStatus(): List<FileData>{
        var filesResult = mutableListOf <FileData>()
        Login()
        var filesList = GetFilesList()

        for(files in filesList)
        {
            try {
                var fileContent = DownloadFile(files.fileId)
                var result = decryptString(fileContent)

                // TODO QDE delete file if under 1 week
                var fileData = ParseFileData(result.replace('\'','"'))
                filesResult.add(fileData)
            }
            catch (e: Exception) {
                println("Response 1 succeeded: ${e.stackTrace}")
            }
        }
        return filesResult
    }

    fun Login(){
        try {
            val url = "https://eapi.pcloud.com/login"

            val formBody = FormBody.Builder()
                .add("username", pcloudUser)
                .add("password", password)
                .build()
            val responseLogin : Response
            val requestlogin = Request.Builder()
                .url(url)
                .post(formBody)
                .build()
            mclient.newCall(requestlogin).execute().use { response -> responseLogin= response }
            println("Response 1 succeeded: ${responseLogin.toString()}")

        }
        catch (e: IOException) {
            println("Response 1 succeeded: ${e.stackTrace}")
        }

        }

    @RequiresApi(Build.VERSION_CODES.O)
    fun ParseFileData(str:String): FileData {
        val result = Json.decodeFromString<FileData>(str)
        return result

    }


}