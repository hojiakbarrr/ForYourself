package com.example.foryourself.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foryourself.data.Result
import com.example.foryourself.data.TestResponse
import com.example.foryourself.data.types.TypeProduct
import com.example.foryourself.databinding.ItemProductBinding

class ExclusiveAdapter : RecyclerView.Adapter<ExclusiveAdapter.ExclusiveAdapterViewHolder>() {


    inner class ExclusiveAdapterViewHolder(val binding: ItemProductBinding) :
            RecyclerView.ViewHolder(binding.root)


    private val diffUtil = object : DiffUtil.ItemCallback<Result>(){
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.objectId == newItem.objectId
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }

    }

    val diffor = AsyncListDiffer(this,diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExclusiveAdapterViewHolder {
        return ExclusiveAdapterViewHolder(
            ItemProductBinding.inflate(
                LayoutInflater.from(parent.context),parent,false
            )
        )
    }

    override fun onBindViewHolder(holder: ExclusiveAdapterViewHolder, position: Int) {
        val product = diffor.currentList[position]

        Glide.with(holder.itemView).load(product.image_main.url).into(holder.binding.productImg)

        holder.binding.productName.text = product.title

        holder.binding.productPrice.text = product.price

    }

    override fun getItemCount(): Int = diffor.currentList.size


}