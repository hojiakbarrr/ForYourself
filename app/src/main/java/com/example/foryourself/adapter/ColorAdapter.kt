package com.example.foryourself.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foryourself.R
import com.example.foryourself.databinding.SizeItemBinding

class ColorAdapter(val clickListener:ItemColorClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var colortList: List<String> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }


    inner class ViewHolder(var binding: SizeItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(size: String) = with(binding){
            binding.apply {
                txtSize.text = size
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.size_item, parent, false)
        val binding = SizeItemBinding.bind(inflater)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var holder = holder as ViewHolder
        holder.bind(colortList[position])
        holder.binding.txtSize.text = colortList[position]
        holder.itemView.setOnClickListener {
            clickListener.ItemColorClick(holder.binding.txtSize.text)
        }
    }

    override fun getItemCount() = colortList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setList(listColor: List<String>){
        colortList = listColor
        notifyDataSetChanged()
    }

    interface ItemColorClickListener {
        fun ItemColorClick(position: CharSequence)

    }

}