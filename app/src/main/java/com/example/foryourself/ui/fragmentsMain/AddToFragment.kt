package com.example.foryourself.ui.fragmentsMain

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
import com.example.foryourself.R
import com.example.foryourself.data.retrofitResponse.order.getOrder.ImageFirst
import com.example.foryourself.data.retrofitResponse.order.getOrder.ImageMain
import com.example.foryourself.data.retrofitResponse.order.getOrder.ImageThird
import com.example.foryourself.data.retrofitResponse.order.postOrder.Result_2
import com.example.foryourself.databinding.AddToFragmentBinding
import com.example.foryourself.ui.activity.MainActivity
import com.example.foryourself.utils.*
import com.example.foryourself.viewmodels.main.AddToViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.parse.ParseFile
import com.parse.ParseObject
import com.parse.SaveCallback
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import java.io.ByteArrayOutputStream

@AndroidEntryPoint
class AddToFragment : Fragment() {
    private val binding: AddToFragmentBinding by lazy {
        AddToFragmentBinding.inflate(layoutInflater)
    }
    private val viewModel: AddToViewModel by viewModels()

    private lateinit var imageMain: ImageMain
    private lateinit var firstMain: ImageFirst
    private lateinit var thirdMain: ImageThird

    private var imageFile_Main: ParseFile? = null
    private var imageFile_Second: ParseFile? = null
    private var imageFile_Third: ParseFile? = null

    private var imageUri_First: Uri? = null
    private var imageUri_Third: Uri? = null
    private var imageUri_Second: Uri? = null

    private var selectedBitmap_MAIN: Bitmap? = null
    private var selectedBitmap_Third: Bitmap? = null
    private var selectedBitmap_Second: Bitmap? = null


    private var type: String? = null
    private var type2: String = "????????????????????"

    private var category: String? = null
    private var category2: String? = null

    private var season: String? = null

    private val loadingDialog: LoadingDialog by lazy(LazyThreadSafetyMode.NONE) {
        LoadingDialog(context = requireContext(), "?????????? ?????????? ?????????????????????? ?????????????????? !!!")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return binding.root
    }

    override fun onResume() {
        super.onResume()
        clearALL()
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility =
            View.VISIBLE
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            reklama.setOnClickListener {
                val action = AddToFragmentDirections.actionAddToFragmentToReklamaFragment()
                Navigation.findNavController(it).navigate(action)
            }
            getMainPhoto.setOnClickListener {
                getImage(IMAGE_MAIN_CODE)
            }
            get2Photo.setOnClickListener {
                getImage(IMAGE_FIRST_CODE)
            }
            get3Photo.setOnClickListener {
                getImage(IMAGE_THIRD_CODE)
            }
        }


        binding.putOnServer.setOnClickListener {
            loadingDialog.show()
//            lifecycleScope.launch {
//                withContext(Dispatchers.IO) {
//
//                    imageFile_Main!!.saveInBackground(SaveCallback { e ->
//                        if (e == null) {
//                            imageFile_Third!!.saveInBackground(SaveCallback { e ->
//                                if (e == null) {
//                                    imageFile_Second!!.saveInBackground(SaveCallback { e ->
//                                        if (e == null) {
//                                            createPost()
//                                        }
//                                    })
//                                }
//                            })
//                        }
//                    })
//                }
//            }


            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
//                    if (imageFile_Main != null) {
//                        imageFile_Main!!.saveInBackground(SaveCallback { e ->
//                            if (e == null) {
//                                toast("?????????????? ???????? ?????????????????????? ???? ????????????")
//                            }
//                        })
//                    } else {
//                        Log.d("SaveImage", "error")
//                    }
//                    if (imageFile_Third != null) {
//                        imageFile_Third!!.saveInBackground(SaveCallback { e ->
//                            if (e == null) {
//                                toast("???????????? ???????????????????????????? ???????? ?????????????????????? ???? ????????????")
//                            }
//                        })
//                    } else {
//                        Log.d("SaveImage", "error")
//                    }
//                    if (imageFile_Second != null) {
//                        imageFile_Second!!.saveInBackground(SaveCallback { e ->
//                            if (e == null) {
//                                toast("???????????? ???????????????????????????? ???????? ?????????????????????? ???? ????????????")
//                            }
//                        })
//                    } else {
//                        Log.d("SaveImage", "error")
//                    }

                    CoroutineScope(Dispatchers.Main).launch {

                        if (imageFile_Main != null) {
                            imageFile_Main!!.saveInBackground(SaveCallback { e ->
                                if (e == null) {
                                    toast("?????????????? ???????? ?????????????????????? ???? ????????????")
                                    if (imageFile_Second != null) {
                                        imageFile_Second!!.saveInBackground(SaveCallback { e ->
                                            if (e == null) {
                                                toast("???????????? ???????????????????????????? ???????? ?????????????????????? ???? ????????????")
                                                if (imageFile_Third != null) {
                                                    imageFile_Third!!.saveInBackground(SaveCallback { e ->
                                                        if (e == null) {
                                                            toast("???????????? ???????????????????????????? ???????? ?????????????????????? ???? ????????????")
                                                            createPost()
                                                            loadingDialog.show()
                                                        }
                                                    })

                                                } else {
                                                    Handler(Looper.getMainLooper()).post {
                                                        toast("???? ???? ?????????????? ???????????? ????????")
                                                        loadingDialog.dismiss()
                                                    }
                                                }
                                            }
                                        })
                                    } else {
                                        Handler(Looper.getMainLooper()).post {
                                            toast("???? ???? ?????????????? ???????????? ????????")
                                        }
                                        loadingDialog.dismiss()
                                    }
                                }
                            })
                        } else {
                            Handler(Looper.getMainLooper()).post {
                                toast("???? ???? ?????????????? ???????????????? ????????")
                            }
                            loadingDialog.dismiss()

//                            if (imageFile_Second != null) {
//                                if (imageFile_Third != null) {
//                                    createPost()
//                                    loadingDialog.show()
//                                } else {
//                                    Handler(Looper.getMainLooper()).post {
//                                        toast("???? ???? ?????????????? ???????????? ????????")
//                                    }
//                                }
//                            } else {
//                                Handler(Looper.getMainLooper()).post {
//                                    toast("???? ???? ?????????????? ???????????? ????????")
//                                }
//                            }
//                        } else {
//                            Handler(Looper.getMainLooper()).post {
//                                toast("???? ???? ?????????????? ???????????????? ????????")
//                            }

                        }
                    }


//                    CoroutineScope(Dispatchers.Main).launch {
//                        delay(6000)
//                        val imageMainFinal =
//                            if (imageFile_Main == null) imageMain else imageFile_Main!!.toImageMain()
//                        val imageFirstFinal =
//                            if (imageFile_Second == null) firstMain else imageFile_Second!!.toImageFirst()
//                        val imageThirdFinal =
//                            if (imageFile_Third == null) thirdMain else imageFile_Third!!.toImageThird()
//                        loadingDialog.dismiss()
//                        createPost(imageMainFinal, imageFirstFinal, imageThirdFinal)
//                    }
                }
            }
        }
        typeProduct()
        categoryy()
        season()
    }

    private fun season() {
        val country = arrayOf("????????", "??????????", "????????", "??????????", "??????????-????????", "??????????-????????")
        var cc: ArrayAdapter<*> =
            ArrayAdapter<Any?>(requireContext(), R.layout.drop_down_item, country)
        binding.filledSeason.apply {
            setAdapter(cc)
            setOnItemClickListener { adapterView, view, i, l ->
                season = text.toString()
            }
        }
    }

    private fun categoryy() {
        val country = arrayOf("???????????? ???? ??????????????", "????????", "??????????", "????????????")
        var bb: ArrayAdapter<*> =
            ArrayAdapter<Any?>(requireContext(), R.layout.drop_down_item, country)
        binding.filledCategory.apply {
            setAdapter(bb)
            setOnItemClickListener { adapterView, view, i, l ->
                category = text.toString()
            }
        }
    }

    private fun typeProduct() {
        val country = arrayOf("???????????? ???? ??????????????", "????????????????????", "??????????????????")
        var aa: ArrayAdapter<*> =
            ArrayAdapter<Any?>(requireContext(), R.layout.drop_down_item, country)
        binding.filledType.apply {
            setAdapter(aa)
            setOnItemClickListener { adapterView, view, i, l ->
                type = binding.filledType.text.toString()
            }
        }
    }

    private fun createPost() {

        binding.apply {
            var typpe = if (type == "???????????? ???? ??????????????") type2 else type
            var categorryy = if (category == "???????????? ???? ??????????????") category2 else category

            var descrip = if (prodDescrip.text.toString().isEmpty()) "????????" else prodDescrip.text.toString().trim()
            var title = if (prodName.text.toString().isEmpty()) "????????" else prodName.text.toString().trim()


            viewModel.addToProduct(
                Result_2(
                    description = descrip,
                    eighthSize = prodSizeEight.text.toString().trim(),
                    fifthSize = prodSizeFive.text.toString().trim(),
                    firstSize = prodSizeOne.text.toString().trim(),
                    image_first = imageFile_Second?.toImageFirst(),
                    image_main = imageFile_Main?.toImageMain(),
                    image_third = imageFile_Third?.toImageThird(),
                    price = prodPrice.text.toString().trim(),
                    secondSize = prodSizeTwo.text.toString().trim(),
                    seventhSize = prodSizeSeven.text.toString().trim(),
                    sixthSize = prodSizeSix.text.toString().trim(),
                    thirdSize = prodSizeThree.text.toString().trim(),
                    title = title,
                    youtubeTrailer = prodTrailer.text.toString().trim(),
                    fourthSize = prodSizeFour.text.toString().trim(),
                    tipy = typpe,
                    season = season,
                    colors = prodOlor1.text.toString().trim(),
                    colors1 = prodOlor2.text.toString().trim(),
                    colors2 = prodOlor3.text.toString().trim(),
                    colors3 = prodOlor4.text.toString().trim(),
                    category = categorryy
                )
            ).observe(viewLifecycleOwner) {
                toast("?????????? ?????????????? ?????? ???????????????? ?????? ??????????????")
                viewModel.ss()
                loadingDialog.dismiss()
                lifecycleScope.launch(Dispatchers.Main) {
                    viewModel.loadingLiveData.observe(viewLifecycleOwner) { it ->
                        startActivity(Intent(requireActivity(), MainActivity::class.java))
                        clearALL()
                    }
                }
            }
        }


    }

    private fun clearALL() {
        binding.apply {
            prodName.text.clear()
            prodDescrip.text.clear()
            prodPrice.text.clear()
            prodTrailer.text.clear()
            prodSizeOne.text.clear()
            prodSizeTwo.text.clear()
            prodSizeThree.text.clear()
            prodSizeFour.text.clear()
            prodSizeFive.text.clear()
            prodSizeSix.text.clear()
            prodSizeSeven.text.clear()
            prodSizeEight.text.clear()

            binding.prodOlor1.text.clear()
            binding.prodOlor2.text.clear()
            binding.prodOlor3.text.clear()
            binding.prodOlor4.text.clear()

            binding.filledType.clearListSelection()
            binding.filledCategory.clearListSelection()
            binding.filledSeason.clearListSelection()


            putMainPhoto.setImageResource(R.drawable.picture)
            put2Photo.setImageResource(R.drawable.picture)
            put3Photo.setImageResource(R.drawable.picture)

        }
    }

    private fun creatPost() {

        val stream = ByteArrayOutputStream()
        selectedBitmap_MAIN?.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val byteArray = stream.toByteArray()
        imageFile_Main = ParseFile("postImage.png", byteArray)
        imageFile_Main!!.saveInBackground(SaveCallback { e ->
            if (e == null) {
                toast("???????????????? ???????? ?????????????? ??????????????????????")
            }
        })

        val stream_2 = ByteArrayOutputStream()
        selectedBitmap_Second?.compress(Bitmap.CompressFormat.PNG, 100, stream_2)
        val byteArray_2 = stream_2.toByteArray()
        imageFile_Second = ParseFile("postImage.png", byteArray_2)
        imageFile_Second!!.saveInBackground(SaveCallback { e ->
            if (e == null) {
                toast("???????????? ???????? ?????????????? ??????????????????????")
            }
        })

        val stream_3 = ByteArrayOutputStream()
        selectedBitmap_Third?.compress(Bitmap.CompressFormat.PNG, 100, stream_3)
        val byteArray_3 = stream_3.toByteArray()
        imageFile_Third = ParseFile("postImage.png", byteArray_3)
        imageFile_Third!!.saveInBackground(SaveCallback { e ->
            if (e == null) {
                toast("???????????? ???????? ?????????????? ??????????????????????")
            }
        })


        val `object` = ParseObject("Orders")

        `object`.put("title", binding.prodName.text.toString().trim())

        `object`.put("description", binding.prodDescrip.text.toString().trim())

        `object`.put("price", binding.prodPrice.text.toString().trim())

        `object`.put("youtubeTrailer", binding.prodTrailer.text.toString().trim())

        `object`.put("firstSize", binding.prodSizeOne.text.toString().trim())

        `object`.put("secondSize", binding.prodSizeTwo.text.toString().trim())

        `object`.put("thirdSize", binding.prodSizeThree.text.toString().trim())

        `object`.put("fourthSize", binding.prodSizeFour.text.toString().trim())

        `object`.put("fifthSize", binding.prodSizeFive.text.toString().trim())

        `object`.put("sixthSize", binding.prodSizeSix.text.toString().trim())

        `object`.put("seventhSize", binding.prodSizeSeven.text.toString().trim())

        `object`.put("eighthSize", binding.prodSizeEight.text.toString().trim())

        `object`.put("image_main", imageFile_Main!!)

        `object`.put("image_first", imageFile_Second!!)

        `object`.put("image_third", imageFile_Third!!)

        `object`.saveInBackground { e ->
            if (e == null) {
                toast("?????????? ?????????????? ?????? ????????????????")
                binding.apply {
                    prodName.text.clear()
                    prodDescrip.text.clear()
                    prodPrice.text.clear()
                    prodTrailer.text.clear()
                    prodSizeOne.text.clear()
                    prodSizeTwo.text.clear()
                    prodSizeThree.text.clear()
                    prodSizeFour.text.clear()
                    prodSizeFive.text.clear()
                    prodSizeSix.text.clear()
                    prodSizeSeven.text.clear()
                    prodSizeEight.text.clear()

                    putMainPhoto.setImageResource(R.drawable.picture)
                    put2Photo.setImageResource(R.drawable.picture)
                    put3Photo.setImageResource(R.drawable.picture)

                }
            } else {
                toast("?????? ???? ?????????? ???? ??????, ???????????????????? ?? ????????????????????????, ?????? ???????????????????? ??????????")
                Log.i("errorToAddPost", "" + e.message)
            }
        }

    }


//    private fun openGallery(code: Int) {
//        val intent = Intent()
//        intent.type = "image/*"
//        intent.action = Intent.ACTION_GET_CONTENT
//        startActivityForResult(intent, code)
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            if (requestCode == IMAGE_FIRST_CODE) {
                imageFile_Second = ParseFile("image.png", image(data.data!!))
                requireContext().uploadImage(data.data!!.toString(), binding.put2Photo)
            }
            if (requestCode == IMAGE_MAIN_CODE) {
                imageFile_Main = ParseFile("image.png", image(data.data!!))
                requireContext().uploadImage(data.data!!.toString(), binding.putMainPhoto)

            }
            if (requestCode == IMAGE_THIRD_CODE) {
                imageFile_Third = ParseFile("image.png", image(data.data!!))
                requireContext().uploadImage(data.data!!.toString(), binding.put3Photo)

            }
        }
    }

    companion object {
        const val IMAGE_FIRST_CODE = 1
        const val IMAGE_MAIN_CODE = 2
        const val IMAGE_THIRD_CODE = 3
    }


}