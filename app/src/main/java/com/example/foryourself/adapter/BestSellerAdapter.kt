package com.example.foryourself.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.example.foryourself.R
import com.example.foryourself.data.retrofitResponse.order.getOrder.Result
import com.example.foryourself.databinding.ItemProductBinding
import com.example.foryourself.ui.fragmentsMain.HomeFragmentDirections
import com.thekhaeng.pushdownanim.PushDownAnim

class BestSellerAdapter : RecyclerView.Adapter<BestSellerAdapter.ViewHolder>() {

    var productList: MutableList<Result> = mutableListOf()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }


    inner class ViewHolder(var binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Result) = with(binding) {

            if (product.isFavorite == true) {
                addToBuy.visibility = View.GONE
                saleee.visibility = View.VISIBLE
            }
            else {
                saleee.visibility = View.GONE
                addToBuy.visibility = View.VISIBLE
            }


            Glide.with(itemView).load(productList[position].image_main?.url)
                .transform(CenterCrop(), GranularRoundedCorners(50f, 50f, 30f, 30f))
                .into(productImg)

            addToBuy.setOnClickListener {
                onItemClickBestseller?.invoke(productList[position])

            }

            PushDownAnim.setPushDownAnimTo(itemView).setOnClickListener { it ->
                try {
                    val action =
                        HomeFragmentDirections.fromHomeFragmentToDetaillFragment((productList[position]))
                    Navigation.findNavController(it).navigate(action)
                } catch (e: Exception) {
                }
            }

            productName.text = product.title
            productPrice.text = product.price

        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BestSellerAdapter.ViewHolder {
        val inflater =
            LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        val binding = ItemProductBinding.bind(inflater)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BestSellerAdapter.ViewHolder, position: Int) {
        holder.bind(productList[position])
    }

    fun updateItem(product: Result) {
        val index = productList.indexOf(product)
        product.isFavorite = true
        productList[index] = product
        notifyItemChanged(index)
    }

    override fun getItemCount() = productList.size

    var onItemClickBestseller: ((Result) -> Unit)? = null




}