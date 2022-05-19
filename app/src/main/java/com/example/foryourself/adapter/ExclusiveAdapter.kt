package com.example.foryourself.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.example.foryourself.data.retrofitResponse.getResponse.Result
import com.example.foryourself.databinding.ItemProductBinding
import com.example.foryourself.db.model.ResultCache

class ExclusiveAdapter : RecyclerView.Adapter<ExclusiveAdapter.ExclusiveAdapterViewHolder>() {

    var onItemClick:((ResultCache) -> Unit) ? = null

    inner class ExclusiveAdapterViewHolder(val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root)


    private val diffUtil = object : DiffUtil.ItemCallback<ResultCache>() {
        override fun areItemsTheSame(oldItem: ResultCache, newItem: ResultCache): Boolean {
            return oldItem.objectId == newItem.objectId
        }

        override fun areContentsTheSame(oldItem: ResultCache, newItem: ResultCache): Boolean {
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
        holder.itemView.setOnClickListener {it ->
            onItemClick!!.invoke(product)
        }

    }

    override fun getItemCount(): Int = diffor.currentList.size


}