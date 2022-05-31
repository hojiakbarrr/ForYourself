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
import com.example.foryourself.databinding.ItemProductCategBinding
import com.example.foryourself.ui.fragmentsDetails.CatFragmentDirections
import com.example.foryourself.ui.fragmentsDetails.TypeFragmentDirections
import com.example.foryourself.ui.fragmentsMain.CategoryFragmentDirections
import com.thekhaeng.pushdownanim.PushDownAnim

class TypeAdapter : RecyclerView.Adapter<TypeAdapter.ExclusiveAdapterViewHolder>() {

    var onItemClick_cate: ((Result) -> Unit)? = null

    inner class ExclusiveAdapterViewHolder(val binding: ItemProductCategBinding) :
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
            ItemProductCategBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }


    override fun onBindViewHolder(holder: ExclusiveAdapterViewHolder, position: Int) {


        val product = diffor.currentList[position]
        Glide.with(holder.itemView)
            .load(product.image_main?.url)
            .transform(CenterCrop(), GranularRoundedCorners(50f, 50f, 50f, 50f))
            .into(holder.binding.productImgCat)


        holder.binding.catName.text = product.title
        holder.binding.catPrice.text = product.price
        holder.binding.catCategorya.text = product.category
        holder.binding.catSeason.text = product.season

        PushDownAnim.setPushDownAnimTo(holder.itemView)
            .setScale(PushDownAnim.MODE_SCALE, 0.89f)
            .setOnClickListener { it ->
                try {
                    val action = TypeFragmentDirections.actionTypeFragmentToDetaillFragment(product)
                    Navigation.findNavController(view = it).navigate(action)
                } catch (e: Exception) {
                }

                try {
                    val action = CatFragmentDirections.actionCatFragmentToDetaillFragment(product)
                    Navigation.findNavController(view = it).navigate(action)
                } catch (e: Exception) {
                }

                try {
                    val action = CategoryFragmentDirections.actionCategoryFragment2ToDetaillFragment(product)
                    Navigation.findNavController(view = it).navigate(action)
                } catch (e: Exception) {
                }
            }
        holder.binding.buyOrder.setOnClickListener {
            onItemClick_cate!!.invoke(product)

        }

    }

    override fun getItemCount(): Int = diffor.currentList.size


}