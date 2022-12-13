package com.ha_remote.clientvm.ui.main

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EntitieViewModel(s: String, s1: String) :  ViewModel(), Observable {
    @Bindable
    var name : MutableLiveData<String> = MutableLiveData(s)

    @Bindable
    var value : MutableLiveData<String> = MutableLiveData(s1)

    private val callbacks: PropertyChangeRegistry by lazy { PropertyChangeRegistry()}

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.add(callback)
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.remove(callback)
    }



}