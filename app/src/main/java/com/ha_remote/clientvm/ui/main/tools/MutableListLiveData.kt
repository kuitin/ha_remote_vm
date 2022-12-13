package com.ha_remote.clientvm.ui.main.tools

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData


class MutableListLiveData<T> :
    MutableLiveData<MutableList<T>>() {
    private val lastModified = MutableLiveData<Long>()
    private var items: MutableList<T>
    private var callback: ListObserver<List<T>>? = null
    fun <T> MutableLiveData<T>.set(value: T)
    {
            postValue(value)
    }

    fun <T> MutableLiveData<T>.get() : T
    {
        return value!!
    }

    fun <T> MutableLiveData<T>.notifyChanged()
    {
        set(get())
    }
    init {
        items = ArrayList()
    }

    fun addItem(item: T) {
        items.add(item)
        onListModified()
    }

    fun removeItem(position: Int) {
        items.removeAt(position)
        onListModified()
    }

    fun updateItem(position: Int, item: T) {
        items[position] = item
        onListModified()
    }

    fun getItem(position: Int): T {
        return items[position]
    }

    private fun onListModified() {
        lastModified.value = System.currentTimeMillis()
        notifyChanged()
    }

    override fun getValue(): MutableList<T>? {
        return items
    }

    override fun setValue(items: MutableList<T>) {
        this.items = items
        onListModified()
    }

    fun observe(owner: LifecycleOwner, callback: ListObserver<List<T>>?) {
        this.callback = callback
        lastModified.observe(owner) { time: Long ->
            onListItemsChanged(
                time
            )
        }
    }

    private fun onListItemsChanged(time: Long) {
        if (callback != null) callback!!.onListItemsChanged(items, items.size)
    }

    interface ListObserver<T> {
        fun onListItemsChanged(items: T, size: Int)
    }
}