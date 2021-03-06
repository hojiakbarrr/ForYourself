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
import com.example.foryourself.data.retrofitResponse.order.getOrder.Result
import com.example.foryourself.databinding.ItemFavoritesBinding
import com.example.foryourself.db.model.FavoritesCache
import com.example.foryourself.ui.fragmentsMain.FavoritesFragmentDirections
import com.example.foryourself.utils.Mapper
import javax.inject.Inject

class FavoritesAdapter(

) : RecyclerView.Adapter<FavoritesAdapter.ExclusiveAdapterViewHolder>() {

    var onItemClicked: ((FavoritesCache) -> Unit)? = null


    inner class ExclusiveAdapterViewHolder(val binding: ItemFavoritesBinding) :
        RecyclerView.ViewHolder(binding.root)


    private val diffUtil = object : DiffUtil.ItemCallback<FavoritesCache>() {
        override fun areItemsTheSame(oldItem: FavoritesCache, newItem: FavoritesCache): Boolean {
            return oldItem.objectId == newItem.objectId
        }

        override fun areContentsTheSame(oldItem: FavoritesCache, newItem: FavoritesCache): Boolean {
            return oldItem == newItem
        }

    }

    val diffor = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExclusiveAdapterViewHolder {
        return ExclusiveAdapterViewHolder(
            ItemFavoritesBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }


    override fun onBindViewHolder(holder: ExclusiveAdapterViewHolder, position: Int) {


        val product = diffor.currentList[position]
        Glide.with(holder.itemView)
            .load(product.image_main?.url)
            .transform(CenterCrop(), GranularRoundedCorners(50f, 50f, 0f, 0f))
            .into(holder.binding.imageView2)


        holder.binding.textname.text = product.title
        holder.binding.textprice.text = product.price.toString() + "  kgz"


        val mapper2: Mapper<FavoritesCache, Result>? = null

        holder.itemView
            .setOnClickListener { it ->
//            onItemClicked!!.invoke(product)
                try {
                    val action = FavoritesFragmentDirections.actionFavoritesFragmentToDetaillFragment(mapper2!!.map(product))
                    Navigation.findNavController(view = it).navigate(action)
                } catch (e: Exception) {
                }
            }
    }

    override fun getItemCount(): Int = diffor.currentList.size


}