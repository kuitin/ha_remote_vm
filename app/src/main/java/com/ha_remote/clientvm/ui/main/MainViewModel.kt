package com.ha_remote.clientvm.ui.main

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel :  ViewModel(), Observable {
    @Bindable
    var inputTitle  = MutableLiveData<String> ()

    @Bindable
    var success = MutableLiveData<Boolean> ()

    public fun onClick()
    {
        success.value = false
        inputTitle.value = "button clicked"
    }

    private val callbacks: PropertyChangeRegistry by lazy { PropertyChangeRegistry()}

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.add(callback)
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.remove(callback)
    }



}