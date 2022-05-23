package com.example.foryourself.ui.fragmentsMain

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.example.foryourself.R
import com.example.foryourself.adapter.ExclusiveAdapter
import com.example.foryourself.databinding.HomeFragmentBinding
import com.example.foryourself.db.model.ResultCache
import com.example.foryourself.utils.LoadingDialog
import com.example.foryourself.utils.toast
import com.example.foryourself.viewmodels.main.HomeViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment(), ExclusiveAdapter.ItemClickListener {
    private lateinit var binding: HomeFragmentBinding

    private lateinit var exclusiveAdapter: ExclusiveAdapter
    private val viewModel: HomeViewModel by viewModels()

    private val loadingDialog: LoadingDialog by lazy(LazyThreadSafetyMode.NONE) {
        LoadingDialog(context = requireContext(), getString(R.string.loading_please_wait))
    }
    private fun exclusiveAdapters() {
        binding.recExclusive.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            binding.recExclusive.adapter = exclusiveAdapter
        }
    }


    override fun onStart() {
        super.onStart()
        viewModel.allOrders().observe(viewLifecycleOwner) {
            exclusiveAdapter.diffor.submitList(it)

        }
        viewModel.loadingLiveData.observe(viewLifecycleOwner) { status ->
            try {
                if (status) loadingDialog.show()
                else loadingDialog.dismiss()
            } catch (e: Exception) {
            }
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) { message ->
            toast(message)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HomeFragmentBinding.inflate(layoutInflater, container, false)
        exclusiveAdapter = ExclusiveAdapter(this)
        exclusiveAdapters()
        return binding.root
    }

    @SuppressLint("CommitPrefEdits")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.allOrders().observe(viewLifecycleOwner) {
            exclusiveAdapter.diffor.submitList(it)

        }
        viewModel.loadingLiveData.observe(viewLifecycleOwner) { status ->
            try {
                if (status) loadingDialog.show()
                else loadingDialog.dismiss()
            } catch (e: Exception) {
            }
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) { message ->
            toast(message)
        }


        val imageList = ArrayList<SlideModel>() // Create image list
        imageList.add(
            SlideModel(
                R.drawable.reklama3,
                scaleType = ScaleTypes.FIT
            )
        )
        imageList.add(
            SlideModel(
                R.drawable.reklama2,
                scaleType = ScaleTypes.FIT
            )
        )
        imageList.add(SlideModel(R.drawable.reklama1, scaleType = ScaleTypes.FIT))

        binding.imageSlider.setImageList(imageList)
        binding.imageSlider.setItemClickListener(object : ItemClickListener {
            override fun onItemSelected(position: Int) {
                toast(position.toString())
            }

        })

//        requireContext().uploadImage2(R.drawable.reklama1, binding.imgForAdvertising)
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

    override fun itemClick(position: ResultCache) {
//        val bundle = Bundle()
//        bundle.putString("data", position)
//        val fragment = HomeFragment()
//        fragment.arguments = bundle
//        this.fragmentManager?.beginTransaction()?.replace(R.id.nav_graph,fragment)
//        transaction?.replace(R.id.nav_graph, fragment)
    }
}