package com.ha_remote.clientvm.ui.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ha_remote.clientvm.ui.main.tools.ActionLiveData
import java.time.ZoneId

class MainViewModel :  ViewModel(), Observable {
    @Bindable
    var inputTitle  : MutableLiveData<String> = MutableLiveData("title")

    @Bindable
    var unableUpdateButton : MutableLiveData<Boolean> = MutableLiveData(true)

    @Bindable
    var unableCleanButton : MutableLiveData<Boolean> = MutableLiveData(true)

    @Bindable
    var enableAlarmButton : MutableLiveData<Boolean> = MutableLiveData(true)

    @Bindable
    var disableAlarmButton : MutableLiveData<Boolean> = MutableLiveData(true)

    val updateButtonAction = ActionLiveData<String>()

    val cleanButtonAction = ActionLiveData<String>()

    val enableAlarmButtonAction = ActionLiveData<String>()

    val disableAlarmButtonAction = ActionLiveData<String>()

    @RequiresApi(Build.VERSION_CODES.O)
    public fun onClick()
    {
        unableUpdateButton.value = false
        updateButtonAction.sendAction("hello")
        inputTitle.value = ZoneId.systemDefault().toString()
    }

    public fun enableUpdateButton() {

        unableUpdateButton.value = true
    }

    public fun onClickCleanButton()
    {
        unableCleanButton.value = false
        cleanButtonAction.sendAction("hello")
    }
    public fun enableCleanButton() {

        unableCleanButton.value = true
    }

    public fun onClickEnableAlarmButton()
    {
        enableAlarmButton.value = false
        enableAlarmButtonAction.sendAction("hello")

    }

    public fun enableEnableAlarmButton() {

        enableAlarmButton.value = true
    }

    public fun onClickDisableAlarmButton()
    {
        disableAlarmButton.value = false
        enableAlarmButton.value = false
        disableAlarmButtonAction.sendAction("hello")

    }

    public fun enableDisableAlarmButton() {

        disableAlarmButton.value = true
    }



    private val callbacks: PropertyChangeRegistry by lazy { PropertyChangeRegistry()}

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.add(callback)
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.remove(callback)
    }



}