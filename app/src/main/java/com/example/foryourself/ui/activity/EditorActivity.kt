package com.example.foryourself.ui.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.foryourself.R
import com.example.foryourself.data.retrofitResponse.getResponse.ImageFirst
import com.example.foryourself.data.retrofitResponse.getResponse.ImageMain
import com.example.foryourself.data.retrofitResponse.getResponse.ImageThird
import com.example.foryourself.data.retrofitResponse.postResponse.Result_2
import com.example.foryourself.databinding.ActivityEditorBinding
import com.example.foryourself.ui.mainFragments.AddToFragment
import com.example.foryourself.utils.*
import com.example.foryourself.viewmodels.detail.EditorViewModel
import com.parse.ParseFile
import com.parse.SaveCallback
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream


@AndroidEntryPoint
class EditorActivity : AppCompatActivity() {
    private val binding: ActivityEditorBinding by lazy {
        ActivityEditorBinding.inflate(layoutInflater)
    }
    private val viewModel: EditorViewModel by viewModels()
    private lateinit var productIdd: String

    companion object {
        const val IMAGE_FIRST_CODE = 1
        const val IMAGE_MAIN_CODE = 2
        const val IMAGE_THIRD_CODE = 3
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
    }

    private lateinit var imageMain: ImageMain
    private lateinit var firstMain: ImageFirst
    private lateinit var thirdMain: ImageThird

    private var imageFile_Main: ParseFile? = null
    private var imageFile_Second: ParseFile? = null
    private var imageFile_Third: ParseFile? = null

    private var imageUri_First: Uri? = null
    private var selectedBitmap_MAIN: Bitmap? = null
    private var imageUri_Third: Uri? = null
    private var selectedBitmap_Third: Bitmap? = null
    private var imageUri_Second: Uri? = null
    private var selectedBitmap_Second: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        getInfo()


        binding.apply {
            putOnServerEdit.setOnClickListener {
                putServer()
            }
            getMainPhotoEdit.setOnClickListener {
                openGallery(IMAGE_MAIN_CODE)
            }
            get2PhotoEdit.setOnClickListener {
                openGallery(IMAGE_FIRST_CODE)
            }
            get3PhotoEdit.setOnClickListener {
                openGallery(IMAGE_THIRD_CODE)
            }
        }

    }

    private fun getInfo() {
        val intent = intent
        productIdd = intent.getStringExtra(Constants.ID_PRODUCT_EDIT)!!
        Log.d("TAssG", productIdd)
        viewModel.getOneOrder(productIdd)
        viewModel.orderLiveData.observe(this) { it ->
            binding.apply {
                try {
                    prodNameEdit.setText(it.title)
                    prodDescripEdit.setText(it.description)
                    prodPriceEdit.setText(it.price)
                    prodTrailerEdit.setText(it.youtubeTrailer)
                    prodSizeOneEdit.setText(it.firstSize)
                    prodSizeTwoEdit.setText(it.secondSize)
                    prodSizeThreeEdit.setText(it.thirdSize)
                    prodSizeFourEdit.setText(it.fourthSize)
                    prodSizeFiveEdit.setText(it.fifthSize)
                    prodSizeSixEdit.setText(it.sixthSize)
                    prodSizeSevenEdit.setText(it.seventhSize)
                    prodSizeEightEdit.setText(it.eighthSize)

                    if (it.image_main != null) {
                        Glide.with(this@EditorActivity)
                            .load(it.image_main.url)
                            .into(putMainPhotoEdit)
                    } else {
                        Glide.with(this@EditorActivity)
                            .load(R.drawable.picture)
                            .into(putMainPhotoEdit)
                    }

                    imageMain = it.image_main!!

                    if (it.image_first != null) {
                        Glide.with(this@EditorActivity)
                            .load(it.image_first.url)
                            .into(put2PhotoEdit)
                    } else {
                        Glide.with(this@EditorActivity)
                            .load(R.drawable.picture)
                            .into(put2PhotoEdit)
                    }

                    firstMain = it.image_first!!


                    if (it.image_third != null) {
                        Glide.with(this@EditorActivity)
                            .load(it.image_third.url)
                            .into(put3PhotoEdit)
                    } else {
                        Glide.with(this@EditorActivity)
                            .load(R.drawable.picture)
                            .into(put3PhotoEdit)
                    }

                    thirdMain = it.image_third!!

                } catch (e: Exception) {
                    toast(e.message.toString())
                }
            }
        }
    }

    private fun putServer() {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                if (imageFile_Main != null) {
                    imageFile_Main!!.saveInBackground(SaveCallback { e ->
                        if (e == null) {
                            toast("Главное фото загрузилось на сервер")
                        }
                    })
                } else {
                    Handler(Looper.getMainLooper()).post {
                        toast("Главное фото Осталось без изменений")
                    }
                }
                if (imageFile_Third != null) {
                    imageFile_Third!!.saveInBackground(SaveCallback { e ->
                        if (e == null) {
                            toast("Второе дополнительное фото загрузилось на сервер")
                        }
                    })
                } else {
                    Handler(Looper.getMainLooper()).post {
                        toast("Второе фото Осталось без изменений")
                    }


                }
                if (imageFile_Second != null) {
                    imageFile_Second!!.saveInBackground(SaveCallback { e ->
                        if (e == null) {
                            toast("Третие дополнительное фото загрузилось на сервер")
                        }
                    })
                } else {
                    Handler(Looper.getMainLooper()).post {
                        toast("Третие фото Осталось без изменений")
                    }

                }
                updatePost()
            }
        }
    }

    private fun updatePost(
    ) {

        val imageMainFinal = if (imageFile_Main == null) imageMain else imageFile_Main!!.toImageMain()
        val imageFirstFinal = if (imageFile_Second == null) firstMain else imageFile_Second!!.toImageFirst()
        val imageThirdFinal = if (imageFile_Third == null) thirdMain else imageFile_Third!!.toImageThird()


        viewModel.updateOrder(
            productIdd,
            Result_2(
                description = binding.prodDescripEdit.text.toString().trim(),
                eighthSize = binding.prodSizeEightEdit.text.toString().trim(),
                fifthSize = binding.prodSizeFiveEdit.text.toString().trim(),
                firstSize = binding.prodSizeOneEdit.text.toString().trim(),
                image_first = imageFirstFinal,
                image_main = imageMainFinal,
                image_third = imageThirdFinal,
                price = binding.prodPriceEdit.text.toString().trim(),
                secondSize = binding.prodSizeTwoEdit.text.toString().trim(),
                seventhSize = binding.prodSizeSevenEdit.text.toString().trim(),
                sixthSize = binding.prodSizeSixEdit.text.toString().trim(),
                thirdSize = binding.prodSizeThreeEdit.text.toString().trim(),
                title = binding.prodNameEdit.text.toString().trim(),
                youtubeTrailer = binding.prodTrailerEdit.text.toString().trim(),
                fourthSize = binding.prodSizeFourEdit.text.toString().trim(),

            )
        )

        viewModel.ss()


//
        lifecycleScope.launch(Dispatchers.Main) {

            viewModel.orderDeleteLiveData.observe(this@EditorActivity) { it ->

                toast(it)
                val intent = Intent(this@EditorActivity, MainActivity::class.java)
                startActivity(intent)
                clearALL()
            }
        }

//        lifecycleScope.launch {
//            withContext(Dispatchers.Main) {
//                viewModel.orderDeleteLiveData.observe(this@EditorActivity) { it ->
//
//                    toast(it)
//                    val intent = Intent(this@EditorActivity,MainActivity::class.java)
//                    startActivity(intent)
//
//                }
//                // Do something
//            }
//        }


    }

    private fun clearALL() {
        binding.apply {
            prodNameEdit.text.clear()
            prodDescripEdit.text.clear()
            prodPriceEdit.text.clear()
            prodTrailerEdit.text.clear()
            prodSizeOneEdit.text.clear()
            prodSizeTwoEdit.text.clear()
            prodSizeThreeEdit.text.clear()
            prodSizeFourEdit.text.clear()
            prodSizeFiveEdit.text.clear()
            prodSizeSixEdit.text.clear()
            prodSizeSevenEdit.text.clear()
            prodSizeEightEdit.text.clear()

            putMainPhotoEdit.setImageResource(R.drawable.picture)
            put2PhotoEdit.setImageResource(R.drawable.picture)
            put3PhotoEdit.setImageResource(R.drawable.picture)

        }
    }


    private fun openGallery(code: Int) {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, code)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            if (requestCode == AddToFragment.IMAGE_FIRST_CODE) {
                imageFile_Second = ParseFile("image.png", image(data.data!!))
                this.uploadImage(data.data!!.toString(), binding.put2PhotoEdit)
            }
            if (requestCode == AddToFragment.IMAGE_MAIN_CODE) {
                imageFile_Main = ParseFile("image.png", image(data.data!!))
                this.uploadImage(data.data!!.toString(), binding.putMainPhotoEdit)

            }
            if (requestCode == AddToFragment.IMAGE_THIRD_CODE) {
                imageFile_Third = ParseFile("image.png", image(data.data!!))
                this.uploadImage(data.data!!.toString(), binding.put3PhotoEdit)

            }
        }
    }
}