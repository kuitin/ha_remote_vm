package com.ha_remote.clientvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ha_remote.clientvm.databinding.ActivityMainBinding
import com.ha_remote.clientvm.ui.main.*


class MainActivity : AppCompatActivity() {

    private lateinit var homeBinding: ActivityMainBinding
    private val mIssuePostLiveData = MutableLiveData<MutableList<EntitieViewModel>>()
//    private var nameList = MutableList<List<EntitieViewModel>>(0,emptyList<EntitieViewModel>())
    private var nameListof : MutableList<EntitieViewModel> = mutableListOf()
    private lateinit var sampleAdapter: EntitiesAdaptater
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        loadData()

        sampleAdapter = EntitiesAdaptater(mIssuePostLiveData)
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
        mainViewModel.success.observe(this) { data ->
            nameListof.add(EntitieViewModel("test","button clicked"))
            mIssuePostLiveData.value = nameListof
            sampleAdapter.updateViewmodel()
        }
    }

    private fun loadData() {
        nameListof.add(EntitieViewModel("1","Sample Title"))
        nameListof.add(EntitieViewModel("2","Sample 1"))
        nameListof.add(EntitieViewModel("3","Sample 2"))
        nameListof.add(EntitieViewModel("4","Sample 3"))
        nameListof.add(EntitieViewModel("5","Sample 4"))
        nameListof.add(EntitieViewModel("6","Sample 5"))
        mIssuePostLiveData.value = nameListof
    }
}

