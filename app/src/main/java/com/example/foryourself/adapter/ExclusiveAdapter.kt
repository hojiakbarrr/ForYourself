package com.example.foryourself.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.example.foryourself.DetaillFragmentDirections
import com.example.foryourself.databinding.ItemProductBinding
import com.example.foryourself.db.model.ResultCache
import com.example.foryourself.ui.fragments.HomeFragmentDirections

class ExclusiveAdapter(val clickListener:ItemClickListener) : RecyclerView.Adapter<ExclusiveAdapter.ExclusiveAdapterViewHolder>() {

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
            Glide.with(holder.itemView)
                .load(product.image_main?.url)
                .transform(CenterCrop(), GranularRoundedCorners(50f, 50f, 30f, 30f))
                .into(holder.binding.productImg)


        holder.binding.productName.text = product.title
        holder.binding.productPrice.text = product.price
        holder.itemView.setOnClickListener {it ->
            onItemClick!!.invoke(product)
//            clickListener.itemClick(product)
//            val action = HomeFragmentDirections.fromHomeFragmentToDetaillFragment(product)
//            Navigation.findNavController(it).navigate(action)
        }

    }

    override fun getItemCount(): Int = diffor.currentList.size

    interface ItemClickListener {
        fun itemClick(position: ResultCache)

    }

}