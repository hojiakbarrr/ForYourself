package com.example.foryourself.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
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

class BestSellerAdapter  : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var productList: List<Result> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }


    inner class ViewHolder(var binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Result) = with(binding) {
            binding.apply {
                productName.text = product.title
                productPrice.text = product.price
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater =
            LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        val binding = ItemProductBinding.bind(inflater)
        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var holder = holder as ViewHolder
        Glide.with(holder.itemView).load(productList[position].image_main?.url)
            .transform(CenterCrop(), GranularRoundedCorners(50f, 50f, 30f, 30f))
            .into(holder.binding.productImg)

        holder.binding.addToBuy.setOnClickListener {
            onItemClickBestseller?.invoke(productList[position])
        }

        PushDownAnim.setPushDownAnimTo(holder.itemView).setOnClickListener { it ->
            try {
                val action = HomeFragmentDirections.fromHomeFragmentToDetaillFragment((productList[position]))
                Navigation.findNavController(it).navigate(action)
            } catch (e: Exception) {
            }
        }

        holder.bind(productList[position])
    }

    override fun getItemCount() = productList.size

    var onItemClickBestseller: ((Result) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setList(listProduct: List<Result>) {
        productList = listProduct
        notifyDataSetChanged()
    }

}