package com.ha_remote.clientvm.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.ha_remote.clientvm.databinding.EntitiesItemBinding
import com.ha_remote.clientvm.R

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

    override fun onBindViewHolder(holder: HomeRecyclerViewHolder, position: Int) {
        when(holder){
            is HomeRecyclerViewHolder.EntitieArrayViewHolder -> holder.bind(items[position] as AbstractViewModel.EntitiesViewModel)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(items[position]){
            is AbstractViewModel.EntitiesViewModel -> R.layout.entities_item
        }
    }


}

sealed class HomeRecyclerViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

    class EntitieArrayViewHolder(private val binding: EntitiesItemBinding) : HomeRecyclerViewHolder(binding){
        fun bind(item: AbstractViewModel.EntitiesViewModel){
            binding.apply { viewmodel = item
//                rvList = adapter
            }
            binding.executePendingBindings()
        }
    }
}
