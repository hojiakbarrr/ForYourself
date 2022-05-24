package com.example.foryourself.ui.fragmentsAdd

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
import com.example.foryourself.data.retrofitResponse.getResponse.ImageFirst
import com.example.foryourself.data.retrofitResponse.getResponse.ImageMain
import com.example.foryourself.data.retrofitResponse.getResponse.ImageThird
import com.example.foryourself.data.retrofitResponse.postResponse.Result_2
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


    private val loadingDialog: LoadingDialog by lazy(LazyThreadSafetyMode.NONE) {
        LoadingDialog(context = requireContext(), "Товар обновляется подождите!!!")
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



                CoroutineScope(Dispatchers.Main).launch {
                    loadingDialog.show()
                    delay(6000)
                    val imageMainFinal = if (imageFile_Main == null) imageMain else imageFile_Main!!.toImageMain()
                    val imageFirstFinal = if (imageFile_Second == null) firstMain else imageFile_Second!!.toImageFirst()
                    val imageThirdFinal = if (imageFile_Third == null) thirdMain else imageFile_Third!!.toImageThird()
                    loadingDialog.dismiss()
                    updatePost(imageMainFinal,imageFirstFinal,imageThirdFinal)

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
        var typpe = if (type == "Ничего не выбрано") type2 else type
        var categorryy = if (category == "Ничего не выбрано") category2 else category



        viewModel.updateOrder(
            args.product.objectId!!,
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
                tipy = typpe,
                season = seasonn,
                colors = binding.prodOlor1Edit.text.toString().trim(),
                colors1 = binding.prodOlor2Edit.text.toString().trim(),
                colors2 = binding.prodOlor3Edit.text.toString().trim(),
                colors3 = binding.prodOlor4Edit.text.toString().trim(),
                category = categorryy
            )
        )



        viewModel.ss()

        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.orderDeleteLiveData.observe(viewLifecycleOwner) { it ->
                loadingDialog.dismiss()
                toast(it)
                Navigation.findNavController(requireView()).navigate(R.id.from_editorrFragment_to_homeFragment)
                clearALL()
            }
        }

//        lifecycleScope.launch {
//            withContext(Dispatchers.Main) {
//                viewModel.orderDeleteLiveData.observe(this@EditorActivity) { it ->
//                    toast(it)
//                    val intent = Intent(this@EditorActivity,MainActivity::class.java)
//                    startActivity(intent)
//                }
//            }
//        }


    }


    override fun onResume() {
        super.onResume()
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility =
            View.GONE
    }

    private fun season() {
        val country = arrayOf("Лето", "Осень", "Зима", "Весна")
        var cc: ArrayAdapter<*> =
            ArrayAdapter<Any?>(requireContext(), R.layout.drop_down_item, country)
        binding.filledSeasonEdit .setAdapter(cc)
        binding.filledSeasonEdit.setOnItemClickListener { adapterView, view, i, l ->
            seasonn = binding.filledSeasonEdit.text.toString()
        }
    }

    private fun categoryy() {
        val country = arrayOf("Ничего не выбрано", "Юбки", "Кофты", "Платья")
        var bb: ArrayAdapter<*> =
            ArrayAdapter<Any?>(requireContext(), R.layout.drop_down_item, country)
        binding.filledCategoryEdit.setAdapter(bb)
        binding.filledCategoryEdit.setOnItemClickListener { adapterView, view, i, l ->
            category = binding.filledCategoryEdit.text.toString()
        }
    }

    private fun typeProduct() {
        val country = arrayOf("Ничего не выбрано", "<Бестселлер>", "Эксклюзив")
        var aa: ArrayAdapter<*> =
            ArrayAdapter<Any?>(requireContext(), R.layout.drop_down_item, country)
        binding.filledTypeEdit.setAdapter(aa)
        binding.filledTypeEdit.setOnItemClickListener { adapterView, view, i, l ->
            type = binding.filledTypeEdit.text.toString()
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

            binding.prodOlor1Edit.text.clear()
            binding.prodOlor2Edit.text.clear()
            binding.prodOlor3Edit.text.clear()
            binding.prodOlor4Edit.text.clear()
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