package com.example.foryourself.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.foryourself.R
import com.example.foryourself.adapter.SizeAdapter
import com.example.foryourself.databinding.ActivityDetailBinding
import com.example.foryourself.utils.Constants
import com.example.foryourself.viewmodels.Detail_viewmodel
import com.example.kapriz.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity(), SizeAdapter.ItemClickListener {
    private val binding: ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }
    private val rotateOpen: Animation by lazy {
        AnimationUtils.loadAnimation(
            this,
            R.anim.rotate_open_anim
        )
    }
    private val rotateClose: Animation by lazy {
        AnimationUtils.loadAnimation(
            this,
            R.anim.rotate_close_anim
        )
    }
    private val toBottom: Animation by lazy {
        AnimationUtils.loadAnimation(
            this,
            R.anim.to_botton_anim
        )
    }
    private val fromBottom: Animation by lazy {
        AnimationUtils.loadAnimation(
            this,
            R.anim.from_botton_anim
        )
    }
    private var clicked: Boolean = false
    private val viewModel: Detail_viewmodel by viewModels()
    private lateinit var productId: String
    private lateinit var youtubeHTTPS: String
    var one: Int = 1
    private lateinit var sizeAdapter: SizeAdapter
    private var sizeList: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        sizeAdapter = SizeAdapter(this)
        prepareAdapter()
        getInfo()
        count()
        binding.apply {
            btnAllFab.setOnClickListener {
                setAnimation(clicked)
                setVisibility(clicked)
                clicked = !clicked
            }
            fab2Editor.setOnClickListener {
                toast("editor")
                Log.d("ee", "Editor")
            }
            fab3DeleteProduct.setOnClickListener {
                toast("delete")
                Log.d("ee", "delete")

            }
        }
    }

    private fun setVisibility(clicked: Boolean) {
        if (!clicked) {
            binding.fab2Editor.visibility = View.VISIBLE
            binding.fab3DeleteProduct.visibility = View.VISIBLE
        } else {
            binding.fab2Editor.visibility = View.INVISIBLE
            binding.fab3DeleteProduct.visibility = View.INVISIBLE
        }

    }


    private fun setAnimation(clicked: Boolean) {
        if (!clicked) {
            binding.btnAllFab.startAnimation(rotateOpen)
            binding.fab2Editor.startAnimation(fromBottom)
            binding.fab3DeleteProduct.startAnimation(fromBottom)
        } else {
            binding.btnAllFab.startAnimation(rotateClose)
            binding.fab2Editor.startAnimation(toBottom)
            binding.fab3DeleteProduct.startAnimation(toBottom)
        }

    }


    private fun prepareAdapter() {
        binding.recBySize.apply {
            layoutManager =
                LinearLayoutManager(this@DetailActivity, LinearLayoutManager.HORIZONTAL, false)
            binding.recBySize.adapter = sizeAdapter
        }
    }

    private fun count() {

        binding.apply {
            minusBtn.setOnClickListener {
                if (countTxt.text.equals("1")) countTxt.setText("1")
                else {
                    one--
                    countTxt.text = one.toString()
                }
            }

            plusBtn.setOnClickListener {
                if (countTxt.text.equals("5")) {
                    countTxt.text = "5"
                    toast("Товар ограничен")
                } else {
                    one++
                    countTxt.text = one.toString()
                }
            }
        }
    }

    private fun getInfo() {
        val intent = intent
        productId = intent.getStringExtra(Constants.ID_PRODUCT)!!
        Log.d("TAG", productId)
        viewModel.getOneOrder(productId)
        viewModel.orderLiveData.observe(this) {
            Log.d("TAG", it.toString())
            binding.apply {
                collapsingToolbar.title = it.title
                Glide.with(this@DetailActivity)
                    .load(it.image_first.url)
                    .into(imgProductDetail)
                txtPrice.setText(it.price)
                txtDescription.setText(it.description)
                youtubeHTTPS = it.youtubeTrailer

                sizeList.add(it.firstSize)
                sizeList.add(it.secondSize)
                sizeList.add(it.thirdSize)
                sizeList.add(it.fourthSize)
                sizeList.add(it.fifthSize)
                sizeList.add(it.sixthSize)
                sizeList.add(it.seventhSize)
                sizeList.add(it.eighthSize)

                sizeAdapter.productList = sizeList

            }
        }
    }

    override fun ItemClick(position: CharSequence) {
        toast(position.toString())
    }

}