package com.example.foryourself.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foryourself.R
import com.example.foryourself.adapter.ExclusiveAdapter
import com.example.foryourself.databinding.HomeFragmentBinding
import com.example.foryourself.ui.detail.DetailActivity
import com.example.foryourself.utils.LoadingDialog
import com.example.foryourself.viewmodels.HomeViewModel
import com.example.foryourself.utils.Constants
import com.example.kapriz.utils.toast
import com.example.kapriz.utils.uploadImage2
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val binding: HomeFragmentBinding by lazy {
        HomeFragmentBinding.inflate(layoutInflater)
    }
    private lateinit var exclusiveAdapter: ExclusiveAdapter
    private val viewModel: HomeViewModel by viewModels()

    private val loadingDialog: LoadingDialog by lazy(LazyThreadSafetyMode.NONE) {
        LoadingDialog(context = requireContext(), getString(R.string.loading_please_wait))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exclusiveAdapter = ExclusiveAdapter()
        exclusiveAdapters()

    }


    private fun exclusiveAdapters() {
        binding.recExclusive.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            binding.recExclusive.adapter = exclusiveAdapter
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        viewModel.allOrders().observe(viewLifecycleOwner) {
//            exclusiveAdapter.diffor.submitList(it)
//        }

        viewModel.getOrders()
        viewModel.orderLiveData.observe(viewLifecycleOwner) { it ->
            exclusiveAdapter.diffor.submitList(it)
        }
        viewModel.loadingLiveData.observe(viewLifecycleOwner) { status ->
            if (status) loadingDialog.show()
            else loadingDialog.dismiss()
        }
        viewModel.errorLiveData.observe(viewLifecycleOwner) { message ->
            toast(message)
        }

        requireContext().uploadImage2(R.drawable.info, binding.imgForAdvertising)
        onClickItem()


//       Glide.with(requireContext())
//            .load(R.drawable.info)
////            .transform(CenterCrop(), GranularRoundedCorners(20f, 60f, 20f, 20f))
//            .apply(RequestOptions.bitmapTransform(RoundedCorners(94)))
//            .into(binding.imgForAdvertising)

    }

    private fun onClickItem() {
        exclusiveAdapter.onItemClick = { t->
            val intent = Intent(activity, DetailActivity::class.java )
            intent.putExtra(Constants.ID_PRODUCT, t.objectId)
            startActivity(intent)
        }
    }

}