package com.example.foryourself.ui.fragmentsMain

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.example.foryourself.R
import com.example.foryourself.adapter.BestSellerAdapter
import com.example.foryourself.adapter.ExclusiveAdapter
import com.example.foryourself.databinding.HomeFragmentBinding
import com.example.foryourself.utils.LoadingDialog
import com.example.foryourself.utils.toast
import com.example.foryourself.viewmodels.main.HomeViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {
    private lateinit var binding: HomeFragmentBinding

    private lateinit var exclusiveAdapter: ExclusiveAdapter
    private lateinit var bestAdapter: BestSellerAdapter
    private val viewModel: HomeViewModel by viewModels()
    private val imageList = ArrayList<SlideModel>() // Create image list


    private val loadingDialog: LoadingDialog by lazy(LazyThreadSafetyMode.NONE) {
        LoadingDialog(context = requireContext(), getString(R.string.loading_please_wait))
    }

    private fun exclusiveAdapters() {
        binding.recExclusive.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            binding.recExclusive.adapter = exclusiveAdapter
        }
        binding.recBests.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            binding.recBests.adapter = bestAdapter
        }
    }


    override fun onStart() {
        super.onStart()
        viewModel.allOrders().observe(viewLifecycleOwner) {
            exclusiveAdapter.diffor.submitList(it)
            binding.swipeToRefresh.isRefreshing = false
        }
        viewModel.orderLiveData.observe(viewLifecycleOwner) {
            bestAdapter.productList = it
            binding.swipeToRefresh.isRefreshing = false
        }
        viewModel.errorLiveData.observe(viewLifecycleOwner) { message -> toast(message) }
        viewModel.loadingLiveData.observe(viewLifecycleOwner) { status ->
            try {
                if (status) loadingDialog.show()
                else loadingDialog.dismiss()
            } catch (e: Exception) {
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HomeFragmentBinding.inflate(layoutInflater, container, false)
        exclusiveAdapter = ExclusiveAdapter()
        bestAdapter = BestSellerAdapter()
        exclusiveAdapters()
        swipe()
        return binding.root
    }

    private fun swipe() {
        binding.apply {
            swipeToRefresh.setOnRefreshListener(this@HomeFragment)
            swipeToRefresh.setColorSchemeColors(
                Color.RED, Color.BLUE, Color.DKGRAY
            )
        }
    }

    @SuppressLint("CommitPrefEdits")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.allOrders().observe(viewLifecycleOwner) {
            exclusiveAdapter.diffor.submitList(it)
            binding.swipeToRefresh.isRefreshing = false
        }
        viewModel.orderLiveData.observe(viewLifecycleOwner) {
            bestAdapter.productList = it
            binding.swipeToRefresh.isRefreshing = false
        }
        viewModel.errorLiveData.observe(viewLifecycleOwner) { message -> toast(message) }
        viewModel.loadingLiveData.observe(viewLifecycleOwner) { status ->
            try {
                if (status) loadingDialog.show()
                else loadingDialog.dismiss()
            } catch (e: Exception) {
            }
        }


        viewModel.getReklama().observe(viewLifecycleOwner) { it ->
            it?.forEach {
                val tt: String = it.reklama1.url
                val ttt: String = it.reklama2.url
                val tttt: String = it.reklama3.url
                imageList.add(SlideModel(tt, scaleType = ScaleTypes.FIT))
                imageList.add(SlideModel(ttt, scaleType = ScaleTypes.FIT))
                imageList.add(SlideModel(tttt, scaleType = ScaleTypes.FIT))
                binding.imageSlider.setImageList(imageList)
            }
        }

        binding.apply {
            imageSlider.setImageList(imageList)
            imageSlider.setItemClickListener(object : ItemClickListener {
                override fun onItemSelected(position: Int) {
                    toast(position.toString())
                }
            })
        }



        binding.apply {
            exclusiveTxt.setOnClickListener {
//            val bottomNavigation = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation)
//            bottomNavigation.selectedItemId = R.id.profileFragment2
//            val manager: FragmentManager = requireActivity().supportFragmentManager
//            manager.beginTransaction().replace(R.id.homeFragment, ProfileFragment()) .addToBackStack(null).commit()

                val action =
                    HomeFragmentDirections.actionHomeFragmentToTypeFragment("Эксклюзив")
                Navigation.findNavController(it).navigate(action)
            }

            bestsellerTxt.setOnClickListener {
                val action =
                    HomeFragmentDirections.actionHomeFragmentToTypeFragment("Бестселлер")
                Navigation.findNavController(it).navigate(action)
            }

        }

        onClickItem()

//       Glide.with(requireContext())
//            .load(R.drawable.info)
////            .transform(CenterCrop(), GranularRoundedCorners(20f, 60f, 20f, 20f))
//            .apply(RequestOptions.bitmapTransform(RoundedCorners(94)))
//            .into(binding.imgForAdvertising)


    }

    override fun onResume() {
        super.onResume()
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility =
            View.VISIBLE
    }

    private fun onClickItem() {
        exclusiveAdapter.onItemClick = { t ->
        }
    }

    override fun onRefresh() {
        binding.apply {
            viewModel.allOrders()
            swipeToRefresh.isRefreshing = true
            swipeToRefresh.postDelayed({
                swipeToRefresh.isRefreshing = false
            }, 1500)
        }
    }

}