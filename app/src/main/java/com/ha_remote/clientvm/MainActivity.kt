package com.ha_remote.clientvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.ha_remote.clientvm.databinding.ActivityMainBinding
import com.ha_remote.clientvm.ui.main.MainFragment
import com.ha_remote.clientvm.ui.main.MainViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var homeBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activilty_main)
        val homeBinding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        // For RecyclerView
// For Text
        val databinding = MainViewModel()
        databinding.inputTitle = "DataBinding QDE"
        homeBinding.username = databinding
//        homeBinding.textName.text = "modified value QDE"
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }
}

