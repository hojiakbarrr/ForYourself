package com.example.foryourself.ui.activity

import android.content.Intent
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
import com.example.foryourself.utils.LoadingDialog
import com.example.foryourself.viewmodels.detail.Detail_viewmodel
import com.example.foryourself.utils.toast
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
    private lateinit var productId: String
    private var clicked: Boolean = false
    private val viewModel: Detail_viewmodel by viewModels()
    private lateinit var youtubeHTTPS: String
    var one: Int = 1
    private lateinit var sizeAdapter: SizeAdapter
    private var sizeList: ArrayList<String> = ArrayList()
    private val loadingDialog: LoadingDialog by lazy(LazyThreadSafetyMode.NONE) {
        LoadingDialog(context = this, "Идет подгрузка данных пождождите")
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
    }


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
                val intent = Intent(this@DetailActivity, EditorActivity::class.java)
                intent.putExtra(Constants.ID_PRODUCT_EDIT, productId)
                startActivity(intent)
            }
            fab3DeleteProduct.setOnClickListener {
                viewModel.deleteOrderBASE(productId)
                viewModel.deleteOrder(productId)
                startActivity(Intent(this@DetailActivity,MainActivity::class.java))
            }
        }
    }


    private fun setVisibility(clicked: Boolean) {
        if (!clicked) {
            binding.fab2Editor.visibility = View.VISIBLE
            binding.fab3DeleteProduct.visibility = View.VISIBLE
            binding.fab3AddToFavProduct.visibility = View.VISIBLE
        } else {
            binding.fab2Editor.visibility = View.INVISIBLE
            binding.fab3DeleteProduct.visibility = View.INVISIBLE
            binding.fab3AddToFavProduct.visibility = View.VISIBLE
        }

    }


    private fun setAnimation(clicked: Boolean) {
        if (!clicked) {
            binding.btnAllFab.startAnimation(rotateOpen)
            binding.fab2Editor.startAnimation(fromBottom)
            binding.fab3AddToFavProduct.startAnimation(fromBottom)
            binding.fab3DeleteProduct.startAnimation(fromBottom)
        } else {
            binding.btnAllFab.startAnimation(rotateClose)
            binding.fab3AddToFavProduct.startAnimation(toBottom)
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
        Log.i("TAGe", productId)
        viewModel.getOneOrder(productId)
        viewModel.orderLiveData.observe(this) {
            Log.d("TAGe", productId)
            binding.apply {
                collapsingToolbar.title = it.title
                Glide.with(this@DetailActivity)
                    .load(it.image_first?.url)
                    .into(imgProductDetail)
                txtPrice.setText(it.price)
                txtDescription.setText(it.description)
                youtubeHTTPS = it.youtubeTrailer!!

                sizeList.add(it.firstSize!!)
                sizeList.add(it.secondSize!!)
                sizeList.add(it.thirdSize!!)
                sizeList.add(it.fourthSize!!)
                sizeList.add(it.fifthSize!!)
                sizeList.add(it.sixthSize!!)
                sizeList.add(it.seventhSize!!)
                sizeList.add(it.eighthSize!!)

                sizeAdapter.productList = sizeList


            }
        }
        viewModel.observeDeleteOrder().observe(this){
            toast(it)
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    override fun ItemClick(position: CharSequence) {
        toast(position.toString())
    }

}