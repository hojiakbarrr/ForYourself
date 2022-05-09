package com.example.foryourself.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foryourself.R
import com.example.foryourself.data.types.TypeProduct
import com.example.foryourself.databinding.ItemProductBinding

class BestSeller : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var productList: List<TypeProduct> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }


    inner class ViewHolder(var binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(product: TypeProduct) = with(binding){
            binding.apply {
                productName.text = product.prod_name
                productPrice.text = product.prod_price
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        val binding = ItemProductBinding.bind(inflater)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var holder = holder as ViewHolder
        Glide.with(holder.itemView).load(productList[position].put_main_photo).into(holder.binding.productImg)
        holder.bind(productList[position])
    }

    override fun getItemCount() = productList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setList(listProduct: List<TypeProduct>){
        productList = listProduct
        notifyDataSetChanged()
    }

}