package com.ha_remote.clientvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ha_remote.clientvm.databinding.ActivityMainBinding
import com.ha_remote.clientvm.ui.main.EntitieData
import com.ha_remote.clientvm.ui.main.EntitiesAdaptater
import com.ha_remote.clientvm.ui.main.MainFragment
import com.ha_remote.clientvm.ui.main.MainViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var homeBinding: ActivityMainBinding
    private var nameList : MutableList<EntitieData> = mutableListOf()
    private lateinit var sampleAdapter: EntitiesAdaptater

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        loadData()
        sampleAdapter = EntitiesAdaptater(nameList)
        DataBindingUtil.setContentView<ActivityMainBinding?>(this, R.layout.activity_main)
            .apply { this.lifecycleOwner = this@MainActivity
            this.username = mainViewModel
            this.rvMain.apply {
                layoutManager= LinearLayoutManager(this@MainActivity)
                adapter=sampleAdapter
            }}
        // For RecyclerView
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }

    private fun loadData() {
        nameList.add(EntitieData("1","Sample Title"))
        nameList.add(EntitieData("2","Sample 1"))
        nameList.add(EntitieData("3","Sample 2"))
        nameList.add(EntitieData("4","Sample 3"))
        nameList.add(EntitieData("5","Sample 4"))
        nameList.add(EntitieData("6","Sample 5"))
    }
}

