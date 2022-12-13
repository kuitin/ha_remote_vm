package com.ha_remote.clientvm.ui.main
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.ha_remote.clientvm.databinding.EntitiesItemBinding

class EntitiesAdaptater(val items: MutableLiveData<MutableList<EntitieViewModel>>)
    : RecyclerView.Adapter<EntitiesAdaptater.ViewHolder>() {
    private lateinit var binding : EntitiesItemBinding
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
        return (items?.value?.size ?: 0) as Int;
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        items?.value?.elementAt(position)?.let { holder.bind(it) }
    }

    inner class ViewHolder(itemView: EntitiesItemBinding) : RecyclerView.ViewHolder(itemView.root){
        // TODO Use dynamic binding
        fun bind(item: EntitieViewModel){
            binding.apply { viewmodel = item
            }
//            notifyDataSetChanged()
        }
    }

}