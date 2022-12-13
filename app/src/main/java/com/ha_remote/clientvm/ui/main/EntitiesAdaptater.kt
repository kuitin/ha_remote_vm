package com.ha_remote.clientvm.ui.main
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ha_remote.clientvm.R
import com.ha_remote.clientvm.databinding.EntitiesItemBinding
class EntitiesAdaptater (val items : MutableList<EntitieViewModel>)
    : RecyclerView.Adapter<EntitiesAdaptater.ViewHolder>() {
    private lateinit var binding : EntitiesItemBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val inflater =LayoutInflater.from(parent.context)
        binding=EntitiesItemBinding.inflate(inflater,parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class ViewHolder(itemView: EntitiesItemBinding) : RecyclerView.ViewHolder(itemView.root){
        // TODO Use dynamic binding
        fun bind(item : EntitieViewModel){
            binding.apply {
                 name.text=item.name.value
                value.text= item.value.value.toString()
            }
        }
    }

}