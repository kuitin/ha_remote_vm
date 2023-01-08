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
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var homeBinding: ActivityMainBinding
    private var nameListof : MutableList<AbstractViewModel> = mutableListOf()
    private var sensors = mutableListOf<AbstractViewModel>()
    private lateinit var sampleAdapter: EntitiesAdaptater
    val scope = CoroutineScope(Job() + Dispatchers.Main)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
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
        mainViewModel.success.observe(this) { data ->
            if(data == false) {
                var pcloud = PCloud ()
                scope.launch(Dispatchers.Default ) {
                    // New coroutine that can call suspend functions
                    var response = pcloud.GetAllStatus()
                    withContext(Dispatchers.Main){
                        RefeshDoorSensorDate(response)
                    }
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

    private fun UpdateItemensor(viewItem:   AbstractViewModel.EntitieRowViewModel,data:   FileData   )  {
        viewItem.name.value =  data.name
        viewItem.value.value = data.state
        viewItem.date.value = data.last_changed.toString()
    }

    private fun CreateNewItemSensor(data:   FileData  )  {
        val temp = nameListof[0] as AbstractViewModel.EntitiesViewModel
        var  viewList = temp.sensorsList as  MutableList<AbstractViewModel.EntitieRowViewModel>
        viewList.add(AbstractViewModel.EntitieRowViewModel(data.name,data.state,data.last_changed))
    }

    private fun RefeshDoorSensorDate(responses: List<FileData> ) {
        for(response in responses) {
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

