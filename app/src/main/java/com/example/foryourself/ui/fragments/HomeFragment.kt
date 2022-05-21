package com.example.foryourself.ui.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.foryourself.R
import com.example.foryourself.adapter.ExclusiveAdapter
import com.example.foryourself.databinding.HomeFragmentBinding
import com.example.foryourself.db.model.ResultCache
import com.example.foryourself.ui.activity.DetailActivity
import com.example.foryourself.utils.Constants
import com.example.foryourself.utils.LoadingDialog
import com.example.foryourself.utils.toast
import com.example.foryourself.viewmodels.detail.Detail_viewmodel
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
    private val viewModel_detail: Detail_viewmodel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



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
        Log.d("rtr", "sfsdf")
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

        Log.i("getOrders.getOrders", "getOrders.getOrders")

//        viewModel.getOrders()
//        viewModel.orderLiveData.observe(viewLifecycleOwner) { it ->
//            exclusiveAdapter.diffor.submitList(it)
//        }
//        viewModel.loadingLiveData.observe(viewLifecycleOwner) { status ->
//            try {
//                if (status) loadingDialog.show()
//                else loadingDialog.dismiss()
//            } catch (e: Exception) {
//            }
//        }
//        viewModel.errorLiveData.observe(viewLifecycleOwner) { message ->
//            toast(message)
//        }

        val imageList = ArrayList<SlideModel>() // Create image list
//        imageList.add(SlideModel("String Url" or R.drawable, "title") You can add title
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

//        requireContext().uploadImage2(R.drawable.reklama1, binding.imgForAdvertising)
        onClickItem()

//       Glide.with(requireContext())
//            .load(R.drawable.info)
////            .transform(CenterCrop(), GranularRoundedCorners(20f, 60f, 20f, 20f))
//            .apply(RequestOptions.bitmapTransform(RoundedCorners(94)))
//            .into(binding.imgForAdvertising)

//        binding.textView3.setOnClickListener {
//            Navigation.findNavController(requireView()).navigate(R.id.from_homeFragment_to_deatilFragment)
//        }

    }

    override fun onResume() {
        super.onResume()
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility =
            View.VISIBLE
    }

    private fun onClickItem() {
        exclusiveAdapter.onItemClick = { t ->
            viewModel_detail.message.postValue(t.objectId)
            Log.i("TAGsse", t.objectId)
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(Constants.ID_PRODUCT, t.objectId)
            startActivity(intent)
            Log.d("TAGe", t.objectId.toString())
//        requireFragmentManager().beginTransaction().replace(R.id.nav_graph, fragment).commit()
//            requireView().findNavController().navigate(R.id.from_homeFragment_to_detaillFragment)

//            requireView().findNavController().navigate(R.id.from_homeFragment_to_deatilFragment)


//            startActivity(intent)
        }
    }

    override fun itemClick(position: ResultCache) {
//        viewModel_detail.getOneOrder(position)
//        val transaction = this.fragmentManager?.beginTransaction()
//
//        val bundle = Bundle()
//        bundle.putString("data", position)
//        val fragment = HomeFragment()
//        fragment.arguments = bundle
//        this.fragmentManager?.beginTransaction()?.replace(R.id.nav_graph,fragment)
//
//        transaction?.replace(R.id.nav_graph, fragment)

//        viewModel_detail.message.postValue(position.objectId)
//        Log.i("TAGsse", position.objectId)
//
//        requireFragmentManager().beginTransaction().replace(R.id.nav_graph, fragment).commit()
//        requireView().findNavController().navigate(R.id.from_homeFragment_to_detaillFragment)


//            val intent = Intent(activity, DetailActivity::class.java)
//            intent.putExtra(Constants.ID_PRODUCT, t.objectId)
    }
}