package com.ha_remote.clientvm.ui.main

import android.R
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ha_remote.clientvm.databinding.EntitiesItemBinding


class EntitiesAdaptater(val items: MutableList<EntitieViewModel>)
    : RecyclerView.Adapter<EntitiesAdaptater.ViewHolder>() {
    private lateinit var binding : EntitiesItemBinding
    private var dataModelList: List<EntitieViewModel>? = null
    private var context: Context? = null
    fun MyRecyclerViewAdapter(dataModelList1: List<EntitieViewModel>, ctx: Context) {
        dataModelList = dataModelList1
        context = ctx
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val inflater =LayoutInflater.from(parent.context)
        binding=EntitiesItemBinding.inflate(inflater,parent,false)

//        notifyDataSetChanged
        return ViewHolder(binding)
    }

    public fun updateViewmodel()
    {
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return (items.size ?: 0) as Int;
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        items.elementAt(position)?.let { holder.bind(it)
            val viewmodel = items[position]
            holder.binding.viewmodel = viewmodel
//        val dataModel: EntitieViewModel = items.get(position)
//        holder.bind(dataModel)
//        holder.itemRowBinding.setItemClickListener(this)
        }
    inner class ViewHolder(val binding: EntitiesItemBinding) : RecyclerView.ViewHolder(binding.root)


//    inner class ViewHolder(itemView: EntitiesItemBinding) : RecyclerView.ViewHolder(itemView.root){
//        // TODO Use dynamic binding
//        fun bind(item: EntitieViewModel){
//            binding.apply { viewmodel = item
//            }
//            binding.executePendingBindings()
////            notifyDataSetChanged()
//        }


}