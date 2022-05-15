package com.example.foryourself.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foryourself.R
import com.example.foryourself.databinding.SizeItemBinding

class SizeAdapter( val clickListener:ItemClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var productList: List<String> = emptyList()
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
        holder.bind(productList[position])
        holder.binding.txtSize.text = productList[position]
        holder.itemView.setOnClickListener {
            clickListener.ItemClick(holder.binding.txtSize.text)
        }
    }

    override fun getItemCount() = productList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setList(listProduct: List<String>){
        productList = listProduct
        notifyDataSetChanged()
    }

    interface ItemClickListener {
        fun ItemClick(position: CharSequence)

    }

}