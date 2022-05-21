package com.example.foryourself.ui.fragments

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
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
import com.example.foryourself.data.retrofitResponse.postResponse.Result_2
import com.example.foryourself.databinding.AddToFragmentBinding
import com.example.foryourself.utils.*
import com.example.foryourself.viewmodels.main.AddToViewModel
import com.parse.ParseFile
import com.parse.ParseObject
import com.parse.SaveCallback
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream

@AndroidEntryPoint
class AddToFragment : Fragment() {
    private val binding: AddToFragmentBinding by lazy {
        AddToFragmentBinding.inflate(layoutInflater)
    }

    private val viewModel: AddToViewModel by viewModels()
    private var imageUri_First: Uri? = null
    private var selectedBitmap_First: Bitmap? = null
    var imageFile_Main: ParseFile? = null


    private var imageUri_Second: Uri? = null
    private var selectedBitmap_Second: Bitmap? = null
    var imageFile_Second: ParseFile? = null

    private var imageUri_Third: Uri? = null
    private var selectedBitmap_Third: Bitmap? = null
    var imageFile_Third: ParseFile? = null

    private var type: String? = null
    private var type2: String? = null

    private var category: String? = null
    private var category2: String? = null

    private var season: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.getMainPhoto.setOnClickListener {
            openGallery(IMAGE_MAIN_CODE)
        }
        binding.get2Photo.setOnClickListener {
            openGallery(IMAGE_FIRST_CODE)
        }
        binding.get3Photo.setOnClickListener {
            openGallery(IMAGE_THIRD_CODE)
        }
        binding.putOnServer.setOnClickListener {

            lifecycleScope.launch {
                withContext(Dispatchers.IO) {

                    imageFile_Main!!.saveInBackground(SaveCallback { e ->
                        if (e == null) {
                            imageFile_Third!!.saveInBackground(SaveCallback { e ->
                                if (e == null) {
                                    imageFile_Second!!.saveInBackground(SaveCallback { e ->
                                        if (e == null) {
                                            createPost()
                                        }
                                    })
                                }
                            })
                        }
                    })
                }
            }
        }
        typeProduct()
        categoryy()
        season()
    }

    private fun season() {
        val country = arrayOf("Лето", "Осень", "Зима", "Весна")
        var cc: ArrayAdapter<*> =
            ArrayAdapter<Any?>(requireContext(), R.layout.drop_down_item, country)

        binding.filledSeason.setAdapter(cc)

        binding.filledSeason.setOnItemClickListener { adapterView, view, i, l ->
//            toast(binding.filledType.text.toString())
            season = binding.filledSeason.text.toString()
        }
    }

    private fun categoryy() {
        val country = arrayOf("Ничего не выбрано", "Юбки", "Кофты", "Платья")
        var bb: ArrayAdapter<*> =
            ArrayAdapter<Any?>(requireContext(), R.layout.drop_down_item, country)

        binding.filledCategory.setAdapter(bb)

        binding.filledCategory.setOnItemClickListener { adapterView, view, i, l ->
//            toast(binding.filledType.text.toString())
            category = binding.filledCategory.text.toString()
        }
    }

    private fun typeProduct() {
        val country = arrayOf("Ничего не выбрано", "<Бестселлер>", "Экслюзивное")
        var aa: ArrayAdapter<*> =
            ArrayAdapter<Any?>(requireContext(), R.layout.drop_down_item, country)

        binding.filledType.setAdapter(aa)

        binding.filledType.setOnItemClickListener { adapterView, view, i, l ->
//            toast(binding.filledType.text.toString())
            type = binding.filledType.text.toString()
        }
    }

    private fun createPost() {
        var typpe = if (type == "Ничего не выбрано") type2 else type
        var categorryy = if (category == "Ничего не выбрано") category2 else category


        viewModel.addToProduct(
            Result_2(
                description = binding.prodDescrip.text.toString().trim(),
                eighthSize = binding.prodSizeEight.text.toString().trim(),
                fifthSize = binding.prodSizeFive.text.toString().trim(),
                firstSize = binding.prodSizeOne.text.toString().trim(),
                image_first = imageFile_Second!!.toImageFirst(),
                image_main = imageFile_Main!!.toImageMain(),
                image_third = imageFile_Third!!.toImageThird(),
                price = binding.prodPrice.text.toString().trim(),
                secondSize = binding.prodSizeTwo.text.toString().trim(),
                seventhSize = binding.prodSizeSeven.text.toString().trim(),
                sixthSize = binding.prodSizeSix.text.toString().trim(),
                thirdSize = binding.prodSizeThree.text.toString().trim(),
                title = binding.prodName.text.toString().trim(),
                youtubeTrailer = binding.prodTrailer.text.toString().trim(),
                fourthSize = binding.prodSizeFour.text.toString().trim(),
                tipy = typpe,
                season = season,
                colors = binding.prodOlor1.text.toString().trim(),
                colors1 = binding.prodOlor2.text.toString().trim(),
                colors2 = binding.prodOlor3.text.toString().trim(),
                colors3 = binding.prodOlor4.text.toString().trim(),
                category = categorryy
            )
        ).observe(viewLifecycleOwner) {
            toast("Товар Успешно был добавлен для продажи")
        }
        viewModel.ss()
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

        Navigation.findNavController(requireView()).navigate(R.id.from_addToFragment_to_homeFragment)
    }

    private fun creatPost() {

        val stream = ByteArrayOutputStream()
        selectedBitmap_First?.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val byteArray = stream.toByteArray()
        imageFile_Main = ParseFile("postImage.png", byteArray)


        val stream_2 = ByteArrayOutputStream()
        selectedBitmap_Second?.compress(Bitmap.CompressFormat.PNG, 100, stream_2)
        val byteArray_2 = stream_2.toByteArray()
        imageFile_Second = ParseFile("postImage.png", byteArray_2)
        imageFile_Second!!.saveInBackground(SaveCallback { e ->
            if (e == null) {
                toast("Второе фото успешно загрузилось")
            }
        })

        val stream_3 = ByteArrayOutputStream()
        selectedBitmap_Third?.compress(Bitmap.CompressFormat.PNG, 100, stream_3)
        val byteArray_3 = stream_3.toByteArray()
        imageFile_Third = ParseFile("postImage.png", byteArray_3)
        imageFile_Third!!.saveInBackground(SaveCallback { e ->
            if (e == null) {
                toast("Третье фото успешно загрузилось")
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
                toast("Товар успешно был добавлен")
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
                toast("Что то пошло не так, обратитесь к разработчику, или попробуйте снова")
                Log.i("errorToAddPost", "" + e.message)
            }
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
            if (requestCode == IMAGE_FIRST_CODE) {
                imageFile_Second = ParseFile("image.png", image(data.data!!))
                requireContext().uploadImage(data.data!!.toString(), binding.put2Photo)
            }
            if (requestCode == IMAGE_MAIN_CODE) {
                imageFile_Main =
                    ParseFile("image.png", image(data.data!!))
                requireContext().uploadImage(data.data!!.toString(), binding.putMainPhoto)

            }
            if (requestCode == IMAGE_THIRD_CODE) {
                imageFile_Third =
                    ParseFile("image.png", image(data.data!!))
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