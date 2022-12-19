package com.ha_remote.clientvm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ha_remote.clientvm.databinding.ActivityMainBinding
import com.ha_remote.clientvm.databinding.EntitiesItemBinding
import com.ha_remote.clientvm.ui.main.*
import com.ha_remote.clientvm.ui.main.AbstractViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var homeBinding: ActivityMainBinding
    private var nameListof : MutableList<AbstractViewModel> = mutableListOf()
    private var sensors = mutableListOf<AbstractViewModel>()
    private lateinit var sampleAdapter: EntitiesAdaptater
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        loadData()

        sampleAdapter = EntitiesAdaptater(nameListof)
        DataBindingUtil.setContentView<ActivityMainBinding?>(this, R.layout.activity_main)
            .apply { this.lifecycleOwner = this@MainActivity
            this.username = mainViewModel
            this.rvMain.apply {
                layoutManager= LinearLayoutManager(this@MainActivity)
                setHasFixedSize(true)
                adapter=sampleAdapter
            }}
//        DataBindingUtil.setContentView<EntitiesItemBinding?>(this, R.layout.activity_main)
//            .apply { this.lifecycleOwner = this@MainActivity
//                rvList.apply {
//                    layoutManager= LinearLayoutManager(this@MainActivity)
//                    setHasFixedSize(true)
//                    adapter=SensorsAdaptater(item.sensorsList)
//                }}
        // For RecyclerView
//        if (savedInstanceState == null) {
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.container, MainFragment.newInstance())
//                .commitNow()
//        }
//        nameListof.observe(owner, this::onListChanged(items, size);
        mainViewModel.success.observe(this) { data ->
            if(data == false) {
//                nameListof.add(EntitieViewModel("test", "button clicked"))
//                nameListof[0].name.value= "11"
                val temp = nameListof[0] as AbstractViewModel.EntitiesViewModel
                var temp2 = temp.sensorsList[0] as AbstractViewModel.EntitieRowViewModel
                temp2.name.value= "11"
            }
            sampleAdapter.notifyItemChanged(0)
//            sampleAdapter.updateViewmodel()
        }
    }
    private fun onListChanged(items: List<Int>, size: Int) {
        // Do Something
    }
    private fun loadData() {
        sensors.add(AbstractViewModel.EntitieRowViewModel("1231", "sensor qde"))
        sensors.add(AbstractViewModel.EntitieRowViewModel("1232", "sensor qde 2"))
        nameListof.add(AbstractViewModel.EntitiesViewModel("1", "Sample Title", sensors))
        nameListof.add(AbstractViewModel.EntitiesViewModel("2","Sample 1"))
//        nameListof.add(AbstractViewModel.EntitiesViewModel("3","Sample 2"))
//        nameListof.add(AbstractViewModel.EntitiesViewModel("4","Sample 3"))
//        nameListof.add(AbstractViewModel.EntitiesViewModel("5","Sample 4"))
//        nameListof.add(AbstractViewModel.EntitiesViewModel("6","Sample 5"))
    }
}

