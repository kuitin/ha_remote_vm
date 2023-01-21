package com.ha_remote.clientvm

import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ha_remote.clientvm.databinding.ActivityMainBinding
import com.ha_remote.clientvm.ui.main.*
import com.ha_remote.clientvm.ui.main.cryptage.DeCryptor
import com.ha_remote.clientvm.ui.main.cryptage.EnCryptor
import com.ha_remote.clientvm.ui.main.cryptage.SaveDatas
import kotlinx.coroutines.*
import java.io.File
import androidx.lifecycle.Observer
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var homeBinding: ActivityMainBinding
    private var nameListof : MutableList<AbstractViewModel> = mutableListOf()
    private var sensors = mutableListOf<AbstractViewModel>()
    private lateinit var sampleAdapter: EntitiesAdaptater
    val scope = CoroutineScope(Job() + Dispatchers.Main)
    private var saveDatas : SaveDatas? = null

    protected fun shouldAskPermissions(): Boolean {
        return Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1
    }

    protected fun askPermissions() {
        val permissions = arrayOf(
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"
        )
        val requestCode = 200
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode)
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        if (shouldAskPermissions()) {
            askPermissions();
        }
        encryptor = EnCryptor()
        decryptor = DeCryptor()
        saveDatas = getApplicationContext().getExternalFilesDir(null)
            ?.let { SaveDatas(it) }!!

        super.onCreate(savedInstanceState)

        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        val mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        InitilazViewItem()

        sampleAdapter = EntitiesAdaptater(nameListof)
        DataBindingUtil.setContentView<ActivityMainBinding?>(this, R.layout.activity_main)
            .apply { this.lifecycleOwner = this@MainActivity
            this.username = mainViewModel
            this.rvMain.apply {
                layoutManager= LinearLayoutManager(this@MainActivity)
                setHasFixedSize(true)
                adapter=sampleAdapter
            }}

        // Observe the snackbar action from the view model and show a snackbar when a new action comes in
        mainViewModel.cleanButtonAction.observe(this, Observer { message ->
            scope.launch(Dispatchers.Default ) {
                var pcloud = PCloud()
                pcloud.RemoveFilesBeforeDate(3)
                withContext(Dispatchers.Main){
                    mainViewModel.enableCleanButton()
                }
            }


        })

        mainViewModel.updateButtonAction.observe(this) { data ->
                try {
                    var temdp = getApplicationContext().getExternalFilesDir(null)
                    val externalStorageDirectory = File(temdp, "testh.txt")
                    var success = externalStorageDirectory.createNewFile()
                    saveDatas?.createFile("testsensor")
                } catch (e: Exception) {
                    println("Response 1 succeeded: ${e.stackTrace}")

                }
                encryptText()
                var pcloud = PCloud ()
                scope.launch(Dispatchers.Default ) {
                    // New coroutine that can call suspend functions
                    var response = pcloud.GetAllStatus()
                    withContext(Dispatchers.Main){
                        RefeshDoorSensorDate(response)
                        mainViewModel.enableUpdateButton()
                    }
                }

        }
    }

    private fun InitilazViewItem() {
        CreateSensorItem()
    }

    private fun CreateSensorItem( )
    {
        nameListof.add(AbstractViewModel.EntitiesViewModel("1", "Sensors", sensors))
    }


    private fun FindItemSensor(name:   String  ) : AbstractViewModel.EntitieRowViewModel? {

        val temp = nameListof[0] as AbstractViewModel.EntitiesViewModel
       var  viewList = temp.sensorsList as  MutableList<AbstractViewModel.EntitieRowViewModel>
        for(viewItem in viewList) {
            // Find sensor to update status
            if(viewItem.name.value == name )
                return viewItem
        }
        return null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun UpdateItemensor(viewItem:   AbstractViewModel.EntitieRowViewModel, data:   FileData   )  {
        viewItem.name.value =  data.name
        viewItem.value.value = data.state
        var local = getResources().getConfiguration().getLocales().get(0)
        var currTimeZone = ZoneId.systemDefault()
        viewItem.date.value = data.last_changed.toInstant().atZone(currTimeZone).toLocalDateTime().format(DateTimeFormatter.ofPattern("dd.MMMM yyyy HH:mm",local)).toString()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun CreateNewItemSensor(data:   FileData  )  {
        val temp = nameListof[0] as AbstractViewModel.EntitiesViewModel
        var  viewList = temp.sensorsList as  MutableList<AbstractViewModel.EntitieRowViewModel>
        var local = getResources().getConfiguration().getLocales().get(0)
        var currTimeZone = ZoneId.systemDefault()
        var last_chaned_local = data.last_changed.toInstant().atZone(currTimeZone).toLocalDateTime().format(DateTimeFormatter.ofPattern("dd.MMMM yyyy HH:mm",local)).toString()
        viewList.add(AbstractViewModel.EntitieRowViewModel(data.name,data.state,last_chaned_local.toString()))
    }


    private fun GetMostRecentEvent(responses: List<FileData> ): List<FileData> {
        var result: List<FileData>
        var distinctSensor = responses.sortedByDescending { it.last_changed }.distinctBy { it.name }

        return distinctSensor

    }

    private val TAG = MainActivity::class.java.simpleName
    private val SAMPLE_ALIAS = "MYALIAS"
    private var encryptor: EnCryptor? = null
    private var decryptor: DeCryptor? = null
    private fun encryptText() {
        try {
            val encryptedText: ByteArray =
                encryptor?.encryptText(SAMPLE_ALIAS, "test") ?: byteArrayOf()

            val result = decryptor?.decryptData(SAMPLE_ALIAS, encryptor?.encryption, encryptor?.iv)


            val tt = "fdesf"
        }
            catch (e: Exception) {
                println("Response 1 succeeded: ${e.stackTrace}")

            }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun RefeshDoorSensorDate(responses: List<FileData> ) {

        var distinctSensor = responses.sortedByDescending { it.last_changed }.distinctBy { it.name }
        for(response in distinctSensor) {
            // Find sensor to update status
            var itemView = FindItemSensor(response.name)
            if( itemView  != null)
            {
                UpdateItemensor(itemView,response)
            }
            else
            {
                CreateNewItemSensor(response)
            }

        }
        // refresh view
        sampleAdapter.notifyDataSetChanged()
    }

}

