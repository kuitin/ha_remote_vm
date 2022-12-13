package com.ha_remote.clientvm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ha_remote.clientvm.databinding.ActivityMainBinding
import com.ha_remote.clientvm.ui.main.*
import com.ha_remote.clientvm.ui.main.tools.MutableListLiveData


class MainActivity : AppCompatActivity() {

    private lateinit var homeBinding: ActivityMainBinding
    private var nameListof : MutableList<EntitieViewModel> = mutableListOf()
//    private var  nameListof : MutableListLiveData<EntitieViewModel> = MutableListLiveData()

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
                adapter=sampleAdapter
            }}
        // For RecyclerView
//        if (savedInstanceState == null) {
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.container, MainFragment.newInstance())
//                .commitNow()
//        }
//        nameListof.observe(owner, this::onListChanged(items, size);
        mainViewModel.success.observe(this) { data ->
            if(data == false) {
                nameListof.add(EntitieViewModel("test", "button clicked"))
                nameListof[0].name.value= "11"
            }
            sampleAdapter.updateViewmodel()
        }
    }
    private fun onListChanged(items: List<Int>, size: Int) {
        // Do Something
    }
    private fun loadData() {
        nameListof.add(EntitieViewModel("1","Sample Title"))
        nameListof.add(EntitieViewModel("2","Sample 1"))
        nameListof.add(EntitieViewModel("3","Sample 2"))
        nameListof.add(EntitieViewModel("4","Sample 3"))
        nameListof.add(EntitieViewModel("5","Sample 4"))
        nameListof.add(EntitieViewModel("6","Sample 5"))
    }
}

