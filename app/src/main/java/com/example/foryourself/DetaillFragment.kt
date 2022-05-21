package com.example.foryourself

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.foryourself.adapter.ExclusiveAdapter
import com.example.foryourself.adapter.SizeAdapter
import com.example.foryourself.databinding.ActivityDetailBinding
import com.example.foryourself.databinding.DetaillFragmentBinding
import com.example.foryourself.databinding.HomeFragmentBinding
import com.example.foryourself.ui.activity.MainActivity
import com.example.foryourself.utils.Constants
import com.example.foryourself.utils.LoadingDialog
import com.example.foryourself.utils.toast
import com.example.foryourself.viewmodels.detail.Detail_viewmodel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class DetaillFragment : Fragment(), SizeAdapter.ItemClickListener {
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
    private lateinit var productId: String
    private var clicked: Boolean = false
    private lateinit var youtubeHTTPS: String
    var one: Int = 1
    private lateinit var sizeAdapter: SizeAdapter
    private var sizeList: ArrayList<String> = ArrayList()
    private val loadingDialog: LoadingDialog by lazy(LazyThreadSafetyMode.NONE) {
        LoadingDialog(context = requireContext(), "Идет подгрузка данных пождождите")
    }
    private val viewModel: Detail_viewmodel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DetaillFragmentBinding.inflate(layoutInflater,container,false)

        sizeAdapter = SizeAdapter(this)
        prepareAdapter()
        getInfo()
        count()
        return binding.root
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private fun prepareAdapter() {
        binding.recBySize.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.recBySize.adapter = sizeAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility =
            View.INVISIBLE
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
        Log.i("werwer", args.toString())

        viewModel.getOneOrder(args.product.objectId)
        viewModel.orderLiveData.observe(viewLifecycleOwner) {
            binding.apply {
                collapsingToolbar.title = it.title
//                Glide.with(this@DetailActivity)
//                    .load(it.image_first?.url)
//                    .into(imgProductDetail)
                val imageList = ArrayList<SlideModel>() // Create image list
//        imageList.add(SlideModel("String Url" or R.drawable, "title") You can add title
                imageList.add(SlideModel(it.image_first?.url, scaleType = ScaleTypes.FIT, ))
                imageList.add(SlideModel(it.image_main?.url, scaleType = ScaleTypes.FIT, ))
                imageList.add(SlideModel(it.image_third?.url, scaleType = ScaleTypes.FIT, ))

                binding.imgProductDetail.setImageList(imageList)

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
                Log.d("TAGsse", it.toString())



            }
        }
        viewModel.observeDeleteOrder().observe(viewLifecycleOwner){
            toast(it)
            startActivity(Intent(requireContext(), MainActivity::class.java))
        }
    }

    override fun ItemClick(position: CharSequence) {
        toast(position.toString())
    }


}