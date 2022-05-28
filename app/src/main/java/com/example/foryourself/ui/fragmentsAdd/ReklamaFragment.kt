package com.example.foryourself.ui.fragmentsAdd

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.foryourself.R
import com.example.foryourself.viewmodels.detail.ReklamaViewModel
import com.example.foryourself.data.retrofitResponse.getReklama.Reklama1
import com.example.foryourself.data.retrofitResponse.getReklama.Reklama2
import com.example.foryourself.data.retrofitResponse.getReklama.Reklama3
import com.example.foryourself.data.retrofitResponse.updateReklama.UpdateReklama
import com.example.foryourself.databinding.ReklamaFragmentBinding
import com.example.foryourself.ui.activity.MainActivity
import com.example.foryourself.utils.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.parse.ParseFile
import com.parse.SaveCallback
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*


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

    private lateinit var reklama1: Reklama1
    private lateinit var reklama2: Reklama2
    private lateinit var reklama3: Reklama3

    companion object {
        const val IMAGE_FIRST_CODE = 1
        const val IMAGE_MAIN_CODE = 2
        const val IMAGE_THIRD_CODE = 3
    }

    private val loadingDialog: LoadingDialog by lazy(LazyThreadSafetyMode.NONE) {
        LoadingDialog(context = requireContext(), "Товар обновляется подождите!!!")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getInfo()

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

    private fun getInfo() {
        loadingDialog.show()
        viewModel.getReklama().observe(viewLifecycleOwner){
            it?.forEach {

                try {
                    Glide.with(requireContext())
                        .load(it.reklama1.url)
                        .into(binding.put1reklama)

                    reklama1 = it.reklama1

                    Glide.with(requireContext())
                        .load(it.reklama2.url)
                        .into(binding.put2reklama)

                    reklama2 = it.reklama2

                    Glide.with(requireContext())
                        .load(it.reklama3.url)
                        .into(binding.put3reklama)

                    reklama3 = it.reklama3
                } catch (e: Exception) {
                    toast(e.message.toString())
                }


            }
        }
        loadingDialog.dismiss()
    }


    private fun creatPost() {

        lifecycleScope.launch {
            withContext(Dispatchers.IO){
                if (imageFile_Mainn != null) {
                    imageFile_Mainn!!.saveInBackground(SaveCallback { e ->
                        if (e == null) {
                            toast("1 реклама загрузилась на сервер")
                        }
                    })
                } else {
                    Handler(Looper.getMainLooper()).post {
                        toast("1 реклама осталась без изменений")
                    }
                }

                if (imageFile_First != null) {
                    imageFile_First!!.saveInBackground(SaveCallback { e ->
                        if (e == null) {
                            toast("Вторая реклама загрузилась на сервер")
                        }
                    })
                } else {
                    Handler(Looper.getMainLooper()).post {
                        toast("Вторая реклама осталось без изменений")
                    }
                }
                if (imageFile_Thirdd != null) {
                    imageFile_Thirdd!!.saveInBackground(SaveCallback { e ->
                        if (e == null) {
                            toast("Третья реклама загрузилась на сервер")
                        }
                    })
                } else {
                    Handler(Looper.getMainLooper()).post {
                        toast("Третья реклама осталась без изменений")
                    }

                }

                CoroutineScope(Dispatchers.Main).launch {
                    loadingDialog.show()
                    delay(6000)
                    val imageMainFinal = if (imageFile_Mainn == null) reklama1 else imageFile_Mainn!!.toImageReklama1()
                    val imageFirstFinal = if (imageFile_First == null) reklama2 else imageFile_First!!.toImageReklama2()
                    val imageThirdFinal = if (imageFile_Thirdd == null) reklama3 else imageFile_Thirdd!!.toImageReklama3()
                    updateReklama(imageMainFinal,imageFirstFinal,imageThirdFinal)

                }

            }
        }

//        imageFile_Mainn!!.saveInBackground(SaveCallback { e ->
//            if (e == null) {
//                toast("Основное фото успешно загрузилось")
//                imageFile_First!!.saveInBackground(SaveCallback { e ->
//                    if (e == null) {
//                        toast("Второе фото успешно загрузилось")
//                        imageFile_Thirdd!!.saveInBackground(SaveCallback { e ->
//                            if (e == null) {
//                                toast("Третье фото успешно загрузилось")
//                                updateReklama()
//                                binding.apply {
//                                    put1reklama.setImageResource(R.drawable.picture)
//                                    put2reklama.setImageResource(R.drawable.picture)
//                                    put3reklama.setImageResource(R.drawable.picture)
//                                }

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
//
//                            }
//                        })
//                    }
//                })
//            }
//        })
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

    private fun updateReklama(
        imageMainFinal: Reklama1,
        imageFirstFinal: Reklama2,
        imageThirdFinal: Reklama3
    ) {
        viewModel.updateReklama(
            "I7GpuYHVhv",
            UpdateReklama(
                reklama1 = imageMainFinal,
                reklama2 = imageFirstFinal,
                reklama3 = imageThirdFinal
            )
        )
        loadingDialog.dismiss()
        toast("Реклама успешна была обновлена")
        startActivity(Intent(requireContext(),MainActivity::class.java))
    }

    override fun onResume() {
        super.onResume()
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility =
            View.GONE
    }
}