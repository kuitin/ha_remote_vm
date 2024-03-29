package com.ha_remote.clientvm.ui.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*
import java.time.ZoneId
import java.time.format.DateTimeFormatter
sealed class AbstractViewModel(): ViewModel(), Observable {

    private val callbacks: PropertyChangeRegistry by lazy { PropertyChangeRegistry() }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.add(callback)
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.remove(callback)
    }
    class EntitiesViewModel(s: String, s1: String, s3 : MutableList<AbstractViewModel> = mutableListOf()) : AbstractViewModel() {
        @Bindable
        var name : MutableLiveData<String> = MutableLiveData(s)

        @Bindable
        var value : MutableLiveData<String> = MutableLiveData(s1)

        @Bindable
        var sensorsList : MutableList<AbstractViewModel> = s3

        fun FillViewModelDatas()
        {

        }


    }
    class EntitieRowViewModel(s: String,s1: String, s2: String) : AbstractViewModel() {
        @Bindable
        var name : MutableLiveData<String> = MutableLiveData(s)

        @Bindable
        var value : MutableLiveData<String> = MutableLiveData(s1)

        @RequiresApi(Build.VERSION_CODES.O)
        @Bindable
        var date : MutableLiveData<String> = MutableLiveData(s2)




    }
}