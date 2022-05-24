package com.example.foryourself.ui.fragmentsMain

import android.annotation.SuppressLint
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
class HomeFragment : Fragment() {
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
        viewModel.allOrders().observe(viewLifecycleOwner) { exclusiveAdapter.diffor.submitList(it) }
        viewModel.orderLiveData.observe(viewLifecycleOwner) { bestAdapter.productList = it }
        viewModel.errorLiveData.observe(viewLifecycleOwner) { message ->toast(message) }
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
        return binding.root
    }

    @SuppressLint("CommitPrefEdits")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.allOrders().observe(viewLifecycleOwner) { exclusiveAdapter.diffor.submitList(it) }
        viewModel.orderLiveData.observe(viewLifecycleOwner) { bestAdapter.productList = it }
        viewModel.errorLiveData.observe(viewLifecycleOwner) { message -> toast(message) }
        viewModel.loadingLiveData.observe(viewLifecycleOwner) { status ->
            try {
                if (status) loadingDialog.show()
                else loadingDialog.dismiss()
            } catch (e: Exception) {
            }
        }

        imageList.add(SlideModel(R.drawable.reklama3, scaleType = ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.reklama2, scaleType = ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.reklama1, scaleType = ScaleTypes.FIT))

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
//            manager.beginTransaction().replace(R.id.homeFragment, ProfileFragment())
//                .addToBackStack(null).commit()
                val action = HomeFragmentDirections.actionHomeFragmentToTypeFragment(exclusiveTxt.text.toString())
                Navigation.findNavController(it).navigate(action)
            }

            bestsellerTxt.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToTypeFragment(bestsellerTxt.text.toString())
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

}