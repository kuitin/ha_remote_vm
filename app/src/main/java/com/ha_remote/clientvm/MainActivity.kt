package com.ha_remote.clientvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.ha_remote.clientvm.databinding.ActivityMainBinding
import com.ha_remote.clientvm.ui.main.MainFragment
import com.ha_remote.clientvm.ui.main.MainViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var homeBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activilty_main)
        val databinding = ViewModelProvider(this)[MainViewModel::class.java]
        val homeBinding: ActivityMainBinding =
            DataBindingUtil.setContentView<ActivityMainBinding?>(this, R.layout.activity_main)
            .apply { this.setLifecycleOwner ( this@MainActivity)
            this.username = databinding}
        // For RecyclerView
// For Text
//        val databinding = MainViewModel()
//        databinding.inputTitle.value = "DataBinding QDE"
//        databinding.success.value = true
//        homeBinding.username = databinding
//        databinding.inputTitle.observe(owner:this, Observable{
//            To
//        })
//        homeBinding.textName.text = "modified value QDE"
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }
}

