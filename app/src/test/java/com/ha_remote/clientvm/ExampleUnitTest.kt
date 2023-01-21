package com.ha_remote.clientvm

import android.util.Log
import com.ha_remote.clientvm.ui.main.PCloud
import org.junit.Assert.*
import org.junit.Test
import java.nio.charset.StandardCharsets


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun sorted_date_isCorrect() {
        var pcloud = PCloud ()
        try {
            var response = pcloud.GetAllStatus()
            var distinctSensor = response.sortedByDescending { it.last_changed }.distinctBy { it.name }
//            var distinctSensor2 = response.last()
            var distinctSensor2 = response.filter { it.name == "Bedroom_window" }.last()
            var distinctSensor3 = response.filter { it.name == "Bedroom_window" }
            print(distinctSensor)
        }
        catch( e: Exception  )
        {
            print(e.message.toString())
        }

        assertEquals(4, 2 + 2)
    }

    @Test
    fun addition_isCorrect() {
        var pcloud = PCloud ()
        try {
            var response = pcloud.GetAllStatus()
            print(response)
        }
        catch( e: Exception  )
        {
            print(e.message.toString())
        }

        assertEquals(4, 2 + 2)
    }

    @Test
    fun parse_filesList() {
        var pcloud = PCloud ()
        var responseStr = "{\n"+
 "\t\"result\": 0,\n"+
 "\t\"metadata\": {\n"+
 "\t\t\"path\": \"\\/\",\n"+
 "\t\t\"name\": \"\\/\",\n"+
 "\t\t\"created\": \"Thu, 01 Dec 2022 13:53:21 +0000\",\n"+
 "\t\t\"ismine\": true,\n"+
 "\t\t\"thumb\": false,\n"+
 "\t\t\"modified\": \"Thu, 01 Dec 2022 13:53:21 +0000\",\n"+
 "\t\t\"id\": \"d0\",\n"+
 "\t\t\"isshared\": false,\n"+
 "\t\t\"icon\": 20,\n"+
 "\t\t\"isfolder\": true,\n"+
 "\t\t\"folderid\": 0,\n"+
 "\t\t\"contents\": [\n"+
 "\t\t\t{\n"+
 "\t\t\t\t\"name\": \"1670188032.csv\",\n"+
 "\t\t\t\t\"created\": \"Sun, 04 Dec 2022 21:07:11 +0000\",\n"+
 "\t\t\t\t\"thumb\": false,\n"+
 "\t\t\t\t\"modified\": \"Thu, 08 Dec 2022 09:24:13 +0000\",\n"+
 "\t\t\t\t\"isfolder\": false,\n"+
 "\t\t\t\t\"fileid\": 19979763782,\n"+
 "\t\t\t\t\"hash\": 7006427866336817990,\n"+
 "\t\t\t\t\"comments\": 0,\n"+
 "\t\t\t\t\"path\": \"\\/1670188032.csv\",\n"+
 "\t\t\t\t\"category\": 4,\n"+
 "\t\t\t\t\"id\": \"f19979763782\",\n"+
 "\t\t\t\t\"isshared\": false,\n"+
 "\t\t\t\t\"ismine\": true,\n"+
 "\t\t\t\t\"size\": 924,\n"+
 "\t\t\t\t\"parentfolderid\": 0,\n"+
 "\t\t\t\t\"contenttype\": \"text\\/csv\",\n"+
 "\t\t\t\t\"icon\": 4\n"+
 "\t\t\t},\n"+
 "\t\t]\n"+
 "\t}\n"+
 "}"
        var response = pcloud.ParseFilesFolders(responseStr)
        assertEquals("1670188032.csv", response?.get(0)?.name)
    }
}