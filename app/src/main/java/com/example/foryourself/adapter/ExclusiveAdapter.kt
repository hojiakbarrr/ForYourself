package com.example.foryourself.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.example.foryourself.data.retrofitResponse.order.getOrder.Result
import com.example.foryourself.databinding.ItemProductBinding
import com.example.foryourself.ui.fragmentsMain.HomeFragmentDirections
import com.thekhaeng.pushdownanim.PushDownAnim
import okhttp3.internal.notify

class ExclusiveAdapter(val clickListener: IconClickListener) :
    RecyclerView.Adapter<ExclusiveAdapter.ExclusiveAdapterViewHolder>() {

    var onItemClick: ((Result) -> Unit)? = null

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
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: ExclusiveAdapterViewHolder, position: Int) {

        val product = diffor.currentList[position]

        Glide.with(holder.itemView)
            .load(product.image_main?.url)
            .transform(CenterCrop(), GranularRoundedCorners(50f, 50f, 30f, 30f))
            .into(holder.binding.productImg)

        holder.binding.apply {
            if (product.isFavorite) {
                addToBuy.visibility = View.GONE
                saleee.visibility = View.VISIBLE
            } else {
                saleee.visibility = View.GONE
                addToBuy.visibility = View.VISIBLE
            }

            productName.text = product.title
            productPrice.text = product.price

            addToBuy.setOnClickListener {
                onItemClick!!.invoke(product)
                clickListener.itemClick(position)
            }
        }

//        holder.binding.addToBuy.setOnClickListener {
//            onItemClick!!.invoke(product)
//        }

        PushDownAnim.setPushDownAnimTo(holder.itemView)
            .setScale(PushDownAnim.MODE_SCALE, 0.89f)
            .setOnClickListener { it ->

                try {
                    val action = HomeFragmentDirections.fromHomeFragmentToDetaillFragment(product)
                    Navigation.findNavController(view = it).navigate(action)
                } catch (e: Exception) {
                }
            }

    }



    fun updateItem(product: Result, positionnn: String) {
        val index = diffor.currentList.indexOf(product)
        product.isFavorite = true
        diffor.currentList.add(product)
        diffor.submitList(diffor.currentList)

        notifyItemChanged(index)
    }

    override fun getItemCount(): Int = diffor.currentList.size


    interface IconClickListener {
        fun itemClick(position: Int)
    }

}