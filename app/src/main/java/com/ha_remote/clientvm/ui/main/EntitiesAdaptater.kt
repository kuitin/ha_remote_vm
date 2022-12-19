package com.ha_remote.clientvm.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.ha_remote.clientvm.R
import com.ha_remote.clientvm.databinding.EntitiesItemBinding

class EntitiesAdaptater(val items: MutableList<AbstractViewModel>)
    : RecyclerView.Adapter<HomeRecyclerViewHolder>() {
    private lateinit var binding : EntitiesItemBinding
    private var dataModelList: List<AbstractViewModel>? = null
    private var context: Context? = null


    fun MyRecyclerViewAdapter(dataModelList1: List<AbstractViewModel>, ctx: Context) {
        dataModelList = dataModelList1
        context = ctx
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeRecyclerViewHolder {
        return when (viewType) {
            R.layout.entities_item -> HomeRecyclerViewHolder.EntitieArrayViewHolder(
                EntitiesItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> throw IllegalArgumentException("Invalid ViewType Provided")
        }
    }

    override fun getItemCount(): Int {
        return (items.size ?: 0) as Int;
    }
    private val viewPool: RecyclerView.RecycledViewPool = RecyclerView.RecycledViewPool()
    override fun onBindViewHolder(holder: HomeRecyclerViewHolder, position: Int) {
        when(holder){
            is HomeRecyclerViewHolder.EntitieArrayViewHolder -> {
                holder.bind(items[position] as AbstractViewModel.EntitiesViewModel)
                val item : AbstractViewModel.EntitiesViewModel = items.get(position) as AbstractViewModel.EntitiesViewModel
                var  layoutManager : LinearLayoutManager?
                        = LinearLayoutManager(holder.rvSubItem?.getContext(),
                    LinearLayoutManager.VERTICAL,
                    false)
                layoutManager!!.initialPrefetchItemCount = item.sensorsList.size

                // Create sub item view adapter

                // Create sub item view adapter
                val subItemAdapter =  SensorsAdaptater(item.sensorsList)

                holder.rvSubItem?.setLayoutManager(layoutManager)
                holder.rvSubItem?.setAdapter(subItemAdapter)
                holder.rvSubItem?.setRecycledViewPool(viewPool)
            }
            else -> {}
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(items[position]){
            is AbstractViewModel.EntitiesViewModel -> R.layout.entities_item
            else -> {0}
        }
    }


}

sealed class HomeRecyclerViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
    public var rvSubItem: RecyclerView ?= null
//    init (itemView: View) {
//
//        rvSubItem = itemView.findViewById(R.id.rvList)
//    }
    class EntitieArrayViewHolder(private val binding: EntitiesItemBinding) : HomeRecyclerViewHolder(binding){

        fun bind(item: AbstractViewModel.EntitiesViewModel){
            binding.apply { viewmodel = item
                rvSubItem = rvList.apply {
                    setHasFixedSize(true)
                    adapter=SensorsAdaptater(item.sensorsList)
                }
             }
            binding.executePendingBindings()
        }
    }
}
