package com.ha_remote.clientvm.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.ha_remote.clientvm.R
import com.ha_remote.clientvm.databinding.EntitieRowBinding
import java.security.AccessController.getContext

class SensorsAdaptater(val items: MutableList<AbstractViewModel>)
    : RecyclerView.Adapter<HomeSensorRecyclerViewHolder>() {
    private lateinit var binding : EntitieRowBinding
    private var dataModelList: List<AbstractViewModel>? = null
    private var context: Context? = null


    fun MyRecyclerViewAdapter(dataModelList1: List<AbstractViewModel>, ctx: Context) {
        dataModelList = dataModelList1
        context = ctx
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeSensorRecyclerViewHolder {
       return HomeSensorRecyclerViewHolder.EntitiesRowViewHolder(
                EntitieRowBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
    }

    override fun getItemCount(): Int {
        return (items.size ?: 0) as Int;
    }

    override fun onBindViewHolder(holder: HomeSensorRecyclerViewHolder, position: Int) {
        when(holder){
            is HomeSensorRecyclerViewHolder.EntitiesRowViewHolder -> holder.bind(items[position] as AbstractViewModel.EntitieRowViewModel)
            else -> {}
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(items[position]){
            is AbstractViewModel.EntitieRowViewModel -> R.layout.entities_item
            else -> {0}
        }
    }


}

sealed class HomeSensorRecyclerViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

    class EntitiesRowViewHolder(private val binding: EntitieRowBinding) : HomeSensorRecyclerViewHolder(binding){
        fun bind(item: AbstractViewModel.EntitieRowViewModel){
            binding.apply { viewmodel = item
              }
            binding.executePendingBindings()
        }
    }
}
