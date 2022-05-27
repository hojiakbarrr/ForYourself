package com.example.foryourself.ui.fragmentsAdd

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.foryourself.R
import com.example.foryourself.adapter.ColorAdapter
import com.example.foryourself.adapter.SizeAdapter
import com.example.foryourself.data.retrofitResponse.getResponse.Result
import com.example.foryourself.databinding.DetaillFragmentBinding
import com.example.foryourself.db.model.ResultCache
import com.example.foryourself.utils.LoadingDialog
import com.example.foryourself.utils.toast
import com.example.foryourself.viewmodels.detail.Detail_viewmodel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetaillFragment : Fragment(), SizeAdapter.ItemClickListener,
    ColorAdapter.ItemColorClickListener {
    private lateinit var binding: DetaillFragmentBinding
    private val args by navArgs<DetaillFragmentArgs>()
    private val rotateOpen: Animation by lazy {
        AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.rotate_open_anim
        )
    }
    private val rotateClose: Animation by lazy {
        AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.rotate_close_anim
        )
    }
    private val toBottom: Animation by lazy {
        AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.to_botton_anim
        )
    }
    private val fromBottom: Animation by lazy {
        AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.from_botton_anim
        )
    }
    private val imageList = ArrayList<SlideModel>() // Create image list
    private lateinit var productId: String
    private var clicked: Boolean = false
    private lateinit var youtubeHTTPS: String
    private var one: Int = 1
    private lateinit var sizeAdapter: SizeAdapter
    private lateinit var colorAdapter: ColorAdapter
    private var sizeList: ArrayList<String> = ArrayList()
    private var colorList: ArrayList<String> = ArrayList()
    private val loadingDialog: LoadingDialog by lazy(LazyThreadSafetyMode.NONE) {
        LoadingDialog(context = requireContext(), "Идет загрузка данных пождождите")
    }
    private val loadingDialogdelete: LoadingDialog by lazy(LazyThreadSafetyMode.NONE) {
        LoadingDialog(context = requireContext(), "Товар удаляется подождите")
    }

    private val viewModel: Detail_viewmodel by viewModels()
    private lateinit var casche: Result


    override fun onResume() {
        super.onResume()
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility =
            View.GONE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DetaillFragmentBinding.inflate(layoutInflater, container, false)
        loadingDialog.show()

        sizeAdapter = SizeAdapter(this)
        colorAdapter = ColorAdapter(this)
        prepareAdapter()
        prepareColorAdapter()
        getInfo()
        count()
        binding.apply {
            btnAllFab.setOnClickListener {
                setAnimation(clicked)
                setVisibility(clicked)
                clicked = !clicked
            }
            fab2Editor.setOnClickListener {
                casche = args.product
                val action = DetaillFragmentDirections.fromDetaillFragmentToEditorrFragment(casche)
                Navigation.findNavController(it).navigate(action)
            }
            fab3DeleteProduct.setOnClickListener {
                loadingDialogdelete.show()
                viewModel.deleteOrderBASE(args.product.objectId!!)
                viewModel.deleteOrder(args.product.objectId!!)
//                val action = DetaillFragmentDirections.fromDetaillFragmentToHomeFragment()
                Navigation.findNavController(it).navigate(R.id.from_detaillFragment_to_homeFragment)
                loadingDialogdelete.dismiss()
            }
            imgYoutube.setOnClickListener {
                openYouTube()

            }
        }
        return binding.root
    }

    private fun openYouTube() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=Iq9yQmVOThE"))
        startActivity(intent)
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
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.recBySize.adapter = sizeAdapter
        }
    }

    private fun prepareColorAdapter() {
        binding.recByColor.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.recByColor.adapter = colorAdapter
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
        viewModel.getOneOrder(args.product.objectId!!)
        viewModel.orderLiveData.observe(viewLifecycleOwner) {
            binding.apply {
                collapsingToolbar.title = it.title
//        imageList.add(SlideModel("String Url" or R.drawable, "title") You can add title
                imageList.add(SlideModel(it.image_first?.url, scaleType = ScaleTypes.FIT))
                imageList.add(SlideModel(it.image_main?.url, scaleType = ScaleTypes.FIT))
                imageList.add(SlideModel(it.image_third?.url, scaleType = ScaleTypes.FIT))

                imgProductDetail.setImageList(imageList)

                txtPrice.setText(it.price)
                txtDescription.setText(it.description)
                youtubeHTTPS = it.youtubeTrailer!!
                prodKategoriya.text = it.category
                prodSeason.text = it.season


                it.firstSize?.let { it1 -> sizeList.add(it1) }
                it.secondSize?.let { it1 -> sizeList.add(it1) }
                it.thirdSize?.let { it1 -> sizeList.add(it1) }
                it.fourthSize?.let { it1 -> sizeList.add(it1) }
                it.fifthSize?.let { it1 -> sizeList.add(it1) }
                it.sixthSize?.let { it1 -> sizeList.add(it1) }
                it.seventhSize?.let { it1 -> sizeList.add(it1) }
                it.eighthSize?.let { it1 -> sizeList.add(it1) }

                sizeAdapter.productList = sizeList

                it.colors?.let { it1 -> colorList.add(it1) }
                it.colors1?.let { it1 -> colorList.add(it1) }
                it.colors2?.let { it1 -> colorList.add(it1) }
                it.colors3?.let { it1 -> colorList.add(it1) }

                colorAdapter.colortList = colorList

            }
        }
        viewModel.observeDeleteOrder().observe(viewLifecycleOwner) {
            toast(it)
        }
        loadingDialog.dismiss()
    }

    override fun ItemClick(position: CharSequence) {
        toast(position.toString())
    }

    override fun ItemColorClick(position: CharSequence) {
        toast(position.toString())
    }
}