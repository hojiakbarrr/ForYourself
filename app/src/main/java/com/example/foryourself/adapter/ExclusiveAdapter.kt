package com.example.foryourself.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideContext
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.foryourself.data.retrofitResponse.Result
import com.example.foryourself.databinding.ItemProductBinding
import com.example.kapriz.utils.uploadImage2

class ExclusiveAdapter : RecyclerView.Adapter<ExclusiveAdapter.ExclusiveAdapterViewHolder>() {


    inner class ExclusiveAdapterViewHolder(val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root)


    private val diffUtil = object : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.objectId == newItem.objectId
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }

    }

    val diffor = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExclusiveAdapterViewHolder {
        return ExclusiveAdapterViewHolder(
            ItemProductBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ExclusiveAdapterViewHolder, position: Int) {
        val product = diffor.currentList[position]
        try {
            Glide.with(holder.itemView)
                .load(product.image_main?.url)
                .transform(CenterCrop(), GranularRoundedCorners(50f, 50f, 30f, 30f))
                .into(holder.binding.productImg)
        } catch (e: Exception) {
            Log.d("test", e.message.toString())
        }

        holder.binding.productName.text = product.title

        holder.binding.productPrice.text = product.price

    }

    override fun getItemCount(): Int = diffor.currentList.size


}