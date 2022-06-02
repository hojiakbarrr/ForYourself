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
import com.example.foryourself.data.retrofitResponse.getResponse.Result
import com.example.foryourself.databinding.ItemProductBinding
import com.example.foryourself.db.model.ResultCache
import com.example.foryourself.repository.OrderRepository
import com.example.foryourself.ui.fragmentsMain.HomeFragmentDirections
import com.example.foryourself.utils.Mapper
import com.thekhaeng.pushdownanim.PushDownAnim
import javax.inject.Inject

class ExclusiveAdapter : RecyclerView.Adapter<ExclusiveAdapter.ExclusiveAdapterViewHolder>() {

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

        holder.binding.addToBuy.setOnClickListener {
            onItemClick!!.invoke(product)
        }

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

    override fun getItemCount(): Int = diffor.currentList.size


}