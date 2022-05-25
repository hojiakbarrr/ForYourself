package com.example.foryourself

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.foryourself.data.retrofitResponse.putReklama.PuttReklama
import com.example.foryourself.databinding.ReklamaFragmentBinding
import com.example.foryourself.ui.activity.MainActivity
import com.example.foryourself.utils.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.parse.ParseException
import com.parse.ParseFile
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.SaveCallback
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream


@AndroidEntryPoint
class ReklamaFragment : Fragment() {
    private val binding: ReklamaFragmentBinding by lazy {
        ReklamaFragmentBinding.inflate(layoutInflater)
    }
    private val viewModel: ReklamaViewModel by viewModels()


    private var imageFile_Mainn: ParseFile? = null
    private var imageFile_First: ParseFile? = null
    private var imageFile_Thirdd: ParseFile? = null

    private var selectedBitmap_MAINN: Bitmap? = null
    private var selectedBitmap_Thirdd: Bitmap? = null
    private var selectedBitmap_First: Bitmap? = null

    var imageUriMain: Uri? = null
    var imageUriSecond: Uri? = null
    var imageUriThird: Uri? = null


    companion object {
        const val IMAGE_FIRST_CODE = 1
        const val IMAGE_MAIN_CODE = 2
        const val IMAGE_THIRD_CODE = 3
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.apply {
            get1reklama.setOnClickListener {
                getImage(IMAGE_MAIN_CODE)
            }
            get2reklama.setOnClickListener {
                getImage(IMAGE_FIRST_CODE)
            }
            get3reklama.setOnClickListener {
                getImage(IMAGE_THIRD_CODE)
            }
            putreklamaserver.setOnClickListener {
                creatPost()
            }
        }
    }


    private fun creatPost() {


        imageFile_Mainn!!.saveInBackground(SaveCallback { e ->
            if (e == null) {
                toast("Основное фото успешно загрузилось")
                imageFile_First!!.saveInBackground(SaveCallback { e ->
                    if (e == null) {
                        toast("Второе фото успешно загрузилось")
                        imageFile_Thirdd!!.saveInBackground(SaveCallback { e ->
                            if (e == null) {
                                toast("Третье фото успешно загрузилось")
                                updateReklama()
                                binding.apply {
                                    put1reklama.setImageResource(R.drawable.picture)
                                    put2reklama.setImageResource(R.drawable.picture)
                                    put3reklama.setImageResource(R.drawable.picture)
                                }

//                                val `object` = ParseObject("Reklama")
//                                `object`.put("reklama1", imageFile_Mainn!!)
//                                `object`.put("reklama2", imageFile_First!!)
//                                `object`.put("reklama3", imageFile_Thirdd!!)
//                                `object`.saveInBackground { e ->
//                                    if (e == null) {
//                                        toast("Товар успешно был добавлен")
//                                        binding.apply {
//                                            put1reklama.setImageResource(R.drawable.picture)
//                                            put2reklama.setImageResource(R.drawable.picture)
//                                            put3reklama.setImageResource(R.drawable.picture)
//                                        }
//                                    } else {
//                                        toast("Что то пошло не так, обратитесь к разработчику, или попробуйте снова")
//                                        Log.i("errorToAddPost", "" + e.message)
//                                    }
//                                }

                            }
                        })
                    }
                })
            }
        })
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null && data.data != null) {

            if (requestCode == IMAGE_FIRST_CODE) {
                imageFile_First = ParseFile("image.png", image(data.data!!))
                requireContext().uploadImage(data.data!!.toString(), binding.put2reklama)

            }
            if (requestCode == IMAGE_MAIN_CODE) {
                imageFile_Mainn = ParseFile("image.png", image(data.data!!))
                requireContext().uploadImage(data.data!!.toString(), binding.put1reklama)

            }
            if (requestCode == IMAGE_THIRD_CODE) {
                imageFile_Thirdd = ParseFile("image.png", image(data.data!!))
                requireContext().uploadImage(data.data!!.toString(), binding.put3reklama)

            }
        }
    }

    private fun updateReklama() {
        viewModel.updateReklama(
            "I7GpuYHVhv",
            PuttReklama(
                reklama1 = imageFile_First?.toImageReklama1(),
                reklama2 = imageFile_Mainn?.toImageReklama2(),
                reklama3 = imageFile_Thirdd?.toImageReklama3()
            )
        )
        toast("Реклама успешна была обновлена")
        startActivity(Intent(requireContext(),MainActivity::class.java))
    }

    override fun onResume() {
        super.onResume()
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility =
            View.GONE
    }
}