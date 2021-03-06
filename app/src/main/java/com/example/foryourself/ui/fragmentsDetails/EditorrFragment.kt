package com.example.foryourself.ui.fragmentsDetails

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.foryourself.R
import com.example.foryourself.data.retrofitResponse.order.getOrder.ImageFirst
import com.example.foryourself.data.retrofitResponse.order.getOrder.ImageMain
import com.example.foryourself.data.retrofitResponse.order.getOrder.ImageThird
import com.example.foryourself.data.retrofitResponse.order.postOrder.Result_2
import com.example.foryourself.databinding.EditorrFragmentBinding
import com.example.foryourself.ui.fragmentsMain.AddToFragment
import com.example.foryourself.utils.*
import com.example.foryourself.viewmodels.detail.EditorViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.parse.ParseFile
import com.parse.SaveCallback
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*


@AndroidEntryPoint
class EditorrFragment : Fragment() {
    private val args by navArgs<EditorrFragmentArgs>()
    private val binding: EditorrFragmentBinding by lazy {
        EditorrFragmentBinding.inflate(layoutInflater)
    }
    private val viewModel: EditorViewModel by viewModels()
    private lateinit var productIdd: String

    companion object {
        const val IMAGE_FIRST_CODE = 1
        const val IMAGE_MAIN_CODE = 2
        const val IMAGE_THIRD_CODE = 3
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


    private var type: String? = null
    private var type2: String? = null

    private var category: String? = null
    private var category2: String? = null

    private var seasonn: String? = null

    private var image_1_boolean: Boolean = false
    private var image_2_boolean: Boolean = false
    private var image_3_boolean: Boolean = false


    private val loadingDialog: LoadingDialog by lazy(LazyThreadSafetyMode.NONE) {
        LoadingDialog(context = requireContext(), "?????????? ?????????????????????? ??????????????????!!!")
    }

    private val loadingDialog2: LoadingDialog by lazy(LazyThreadSafetyMode.NONE) {
        LoadingDialog(context = requireContext(), "?????????????????? ?????????? ??????????????????????")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        getInfo()


        binding.apply {
            putOnServerEdit.setOnClickListener {
                putServer()
            }
            getMainPhotoEdit.setOnClickListener {
                getImage(IMAGE_MAIN_CODE)
            }
            get2PhotoEdit.setOnClickListener {
                getImage(IMAGE_FIRST_CODE)
            }
            get3PhotoEdit.setOnClickListener {
                getImage(IMAGE_THIRD_CODE)
            }
        }
        typeProduct()
        categoryy()
        season()

        return binding.root
    }

    private fun getInfo() {
        viewModel.getOneOrder(args.product.objectId!!)
        viewModel.orderLiveData.observe(viewLifecycleOwner) { it ->
            loadingDialog2.show()
            binding.apply {
                try {
                    Log.d("TAssG", it.toString())
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
                    edit1.setText(it.colors)
                    edit2.setText(it.colors1)
                    edit3.setText(it.colors2)
                    edit4.setText(it.colors3)
                    filledCategoryEdit.setText(it.category)
                    filledSeasonEdit.setText(it.season)
                    filledTypeEdit.setText(it.tipy)

                    if (it.image_main != null) {
                        Glide.with(requireContext())
                            .load(it.image_main.url)
                            .into(putMainPhotoEdit)
                    } else {
                        Glide.with(requireContext())
                            .load(R.drawable.picture)
                            .into(putMainPhotoEdit)
                    }

                    imageMain = it.image_main!!

                    if (it.image_first != null) {
                        Glide.with(requireContext())
                            .load(it.image_first.url)
                            .into(put2PhotoEdit)
                    } else {
                        Glide.with(requireContext())
                            .load(R.drawable.picture)
                            .into(put2PhotoEdit)
                    }

                    firstMain = it.image_first!!


                    if (it.image_third != null) {
                        Glide.with(requireContext())
                            .load(it.image_third.url)
                            .into(put3PhotoEdit)
                    } else {
                        Glide.with(requireContext())
                            .load(R.drawable.picture)
                            .into(put3PhotoEdit)
                    }

                    thirdMain = it.image_third!!
                } catch (e: Exception) {
                    snaketoast(e.message.toString(), requireView())
                }
            }
            loadingDialog2.dismiss()
        }
    }

    private fun putServer() {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                if (imageFile_Main != null) {
                    imageFile_Main!!.saveInBackground(SaveCallback { e ->
                        if (e == null) {
                            image_1_boolean = true
                            toast("?????????????? ???????? ?????????????????????? ???? ????????????")
                        }
                    })
                } else {
                    Handler(Looper.getMainLooper()).post {
                        toast("?????????????? ???????? ???????????????? ?????? ??????????????????")
                        image_1_boolean = true
                    }
                }
                if (imageFile_Third != null) {
                    imageFile_Third!!.saveInBackground(SaveCallback { e ->
                        if (e == null) {
                            toast("???????????? ???????????????????????????? ???????? ?????????????????????? ???? ????????????")
                            image_2_boolean = true
                        }
                    })
                } else {
                    Handler(Looper.getMainLooper()).post {
                        toast("???????????? ???????? ???????????????? ?????? ??????????????????")
                        image_2_boolean = true
                    }
                }
                if (imageFile_Second != null) {
                    imageFile_Second!!.saveInBackground(SaveCallback { e ->
                        if (e == null) {
                            toast("???????????? ???????????????????????????? ???????? ?????????????????????? ???? ????????????")
                            image_3_boolean = true
                        }
                    })
                } else {
                    Handler(Looper.getMainLooper()).post {
                        toast("???????????? ???????? ???????????????? ?????? ??????????????????")
                        image_3_boolean = true
                    }

                }

                CoroutineScope(Dispatchers.Main).launch {
                    loadingDialog.show()
                    delay(8000)
                    if (image_1_boolean == true) {
                        val imageMainFinal =
                            if (imageFile_Main == null) imageMain else imageFile_Main!!.toImageMain()
                        if (image_2_boolean == true) {
                            val imageFirstFinal =
                                if (imageFile_Second == null) firstMain else imageFile_Second!!.toImageFirst()
                            if (image_3_boolean == true) {
                                val imageThirdFinal =
                                    if (imageFile_Third == null) thirdMain else imageFile_Third!!.toImageThird()
                                updatePost(imageMainFinal, imageFirstFinal, imageThirdFinal)
                            }
                        }
                    }
                }
            }
        }
    }


    private fun updatePost(
        imageMainFinal: ImageMain,
        imageFirstFinal: ImageFirst,
        imageThirdFinal: ImageThird
    ) {
        loadingDialog.show()
        var typpe = if (type == "???????????? ???? ??????????????") type2 else type
        var categorryy = if (category == "???????????? ???? ??????????????") category2 else category

        binding.apply {

            viewModel.updateOrder(
                args.product.objectId!!,
                Result_2(
                    description = prodDescripEdit.text.toString().trim(),
                    eighthSize = prodSizeEightEdit.text.toString().trim(),
                    fifthSize = prodSizeFiveEdit.text.toString().trim(),
                    firstSize = prodSizeOneEdit.text.toString().trim(),
                    image_first = imageFirstFinal,
                    image_main = imageMainFinal,
                    image_third = imageThirdFinal,
                    price = prodPriceEdit.text.toString().trim(),
                    secondSize = prodSizeTwoEdit.text.toString().trim(),
                    seventhSize = prodSizeSevenEdit.text.toString().trim(),
                    sixthSize = prodSizeSixEdit.text.toString().trim(),
                    thirdSize = prodSizeThreeEdit.text.toString().trim(),
                    title = prodNameEdit.text.toString().trim(),
                    youtubeTrailer = prodTrailerEdit.text.toString().trim(),
                    fourthSize = prodSizeFourEdit.text.toString().trim(),
                    tipy = typpe,
                    season = seasonn,
                    colors = edit1.text.toString().trim(),
                    colors1 = edit2.text.toString().trim(),
                    colors2 = edit3.text.toString().trim(),
                    colors3 = edit4.text.toString().trim(),
                    category = categorryy
                )
            )

        }





        viewModel.ss()

        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.orderDeleteLiveData.observe(viewLifecycleOwner) { it ->
                loadingDialog.dismiss()
                snaketoast(it, requireView())
                Navigation.findNavController(requireView())
                    .navigate(R.id.from_editorrFragment_to_homeFragment)
                clearALL()
            }
        }
    }


    override fun onResume() {
        super.onResume()
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility =
            View.GONE
    }

    private fun season() {
        val country = arrayOf("????????", "??????????", "????????", "??????????", "??????????-????????", "??????????-????????")
        var cc: ArrayAdapter<*> =
            ArrayAdapter<Any?>(requireContext(), R.layout.drop_down_item, country)
        binding.filledSeasonEdit.apply {
            setAdapter(cc)
            setOnItemClickListener { adapterView, view, i, l ->
                seasonn = text.toString()
            }
        }
    }

    private fun categoryy() {
        val country = arrayOf("???????????? ???? ??????????????", "????????", "??????????", "????????????")
        var bb: ArrayAdapter<*> =
            ArrayAdapter<Any?>(requireContext(), R.layout.drop_down_item, country)

        binding.filledCategoryEdit.apply {
            setAdapter(bb)
            setOnItemClickListener { adapterView, view, i, l ->
                category = text.toString()
            }
        }
//        binding.filledCategoryEdit.setAdapter(bb)
//        binding.filledCategoryEdit.setOnItemClickListener { adapterView, view, i, l ->
//            category = binding.filledCategoryEdit.text.toString()
//        }
    }

    private fun typeProduct() {
        val country = arrayOf("???????????? ???? ??????????????", "<????????????????????>", "??????????????????")
        var aa: ArrayAdapter<*> =
            ArrayAdapter<Any?>(requireContext(), R.layout.drop_down_item, country)

        binding.filledTypeEdit.apply {
            setAdapter(aa)
            setOnItemClickListener { adapterView, view, i, l ->
                type = text.toString()
            }
        }
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

            binding.edit1.text.clear()
            binding.edit2.text.clear()
            binding.edit3.text.clear()
            binding.edit4.text.clear()
            binding.filledTypeEdit.clearListSelection()
            binding.filledCategoryEdit.clearListSelection()
            binding.filledSeasonEdit.clearListSelection()


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
                requireContext().uploadImage(data.data!!.toString(), binding.put2PhotoEdit)
            }
            if (requestCode == AddToFragment.IMAGE_MAIN_CODE) {
                imageFile_Main = ParseFile("image.png", image(data.data!!))
                requireContext().uploadImage(data.data!!.toString(), binding.putMainPhotoEdit)

            }
            if (requestCode == AddToFragment.IMAGE_THIRD_CODE) {
                imageFile_Third = ParseFile("image.png", image(data.data!!))
                requireContext().uploadImage(data.data!!.toString(), binding.put3PhotoEdit)

            }
        }
    }


}