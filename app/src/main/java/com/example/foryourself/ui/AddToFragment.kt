package com.example.foryourself.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.foryourself.R
import com.example.foryourself.databinding.AddToFragmentBinding
import com.example.foryourself.viewmodels.AddToViewModel
import com.example.kapriz.utils.toast
import com.parse.ParseFile
import com.parse.ParseObject
import com.parse.SaveCallback
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream

@AndroidEntryPoint
class AddToFragment : Fragment() {
    private val binding: AddToFragmentBinding by lazy {
        AddToFragmentBinding.inflate(layoutInflater)
    }

    private val viewModel: AddToViewModel by viewModels()

    var imageUri_First: Uri? = null
    var selectedBitmap_First: Bitmap? = null
    var imageFile_First: ParseFile? = null

    var imageUri_Second: Uri? = null
    var selectedBitmap_Second: Bitmap? = null
    var imageFileFirst_Second: ParseFile? = null

    var imageUri_Third: Uri? = null
    var selectedBitmap_Third: Bitmap? = null
    var imageFileFirst_Third: ParseFile? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.getMainPhoto.setOnClickListener {
            openGallery()
        }
        binding.get2Photo.setOnClickListener {
            openGallery()
        }
        binding.get3Photo.setOnClickListener {
            openGallery()
        }
        binding.putOnServer.setOnClickListener {
            creatPost()
        }


    }

    private fun creatPost() {

        val stream = ByteArrayOutputStream()
        selectedBitmap_First?.compress(Bitmap.CompressFormat.PNG,100,stream)
        val byteArray = stream.toByteArray()
        val parseFile = ParseFile("postImage.png",byteArray)
        parseFile.saveInBackground(SaveCallback {e->
            if (e == null){
                toast("Первое фото успешно загрузилось")
            }
        })

        val stream_2 = ByteArrayOutputStream()
        selectedBitmap_Second?.compress(Bitmap.CompressFormat.PNG,100,stream_2)
        val byteArray_2 = stream_2.toByteArray()
        val parseFile_2 = ParseFile("postImage.png",byteArray_2)
        parseFile_2.saveInBackground(SaveCallback {e->
            if (e == null){
                toast("Второе фото успешно загрузилось")
            }
        })

        val stream_3 = ByteArrayOutputStream()
        selectedBitmap_Third?.compress(Bitmap.CompressFormat.PNG,100,stream_3)
        val byteArray_3 = stream_3.toByteArray()
        val parseFile_3 = ParseFile("postImage.png",byteArray_3)
        parseFile_3.saveInBackground(SaveCallback {e->
            if (e == null){
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

        `object`.put("image_main", parseFile)

        `object`.put("image_first", parseFile_2)

        `object`.put("image_third", parseFile_3)

        `object`.saveInBackground {e ->
            if (e == null){
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
            }else{
                toast("Что то пошло не так, обратитесь к разработчику, или попробуйте снова")
                Log.i("errorToAddPost", "" + e.message)
            }
        }

    }





    private fun openGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            if (imageUri_First == null){
                imageUri_First = data.data
                try {
                    selectedBitmap_First = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, imageUri_First)

                    binding.apply {
                        putMainPhoto.setImageBitmap(selectedBitmap_First)
                    }
                } catch (e: Exception) {
                    Log.d("error", "" + e.message)
                }

            }else if (imageUri_Second == null){
                imageUri_Second = data.data
                try {
                    selectedBitmap_Second = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, imageUri_Second)

                    binding.apply {
                        put2Photo.setImageBitmap(selectedBitmap_Second)
                    }
                } catch (e: Exception) {
                    Log.d("error", "" + e.message)
                }


            }else{
                imageUri_Third = data.data

                try {
                    selectedBitmap_Third = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, imageUri_Third)

                    binding.apply {
                        put3Photo.setImageBitmap(selectedBitmap_Third)
                    }
                } catch (e: Exception) {
                    Log.d("error", "" + e.message)
                }

            }

//            uploadImage()
        }
    }


    private fun uploadImage() {

        val stream = ByteArrayOutputStream()
        selectedBitmap_First?.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val byteArray = stream.toByteArray()
        val parseFile = ParseFile("postImage.png", byteArray)
        parseFile.saveInBackground(SaveCallback { e ->
            if (e == null) {
                toast("Фото сохранилось")
                imageFile_First = parseFile
            }
        })
    }

}