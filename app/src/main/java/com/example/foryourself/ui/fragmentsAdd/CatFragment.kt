package com.example.foryourself.ui.fragmentsAdd

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foryourself.viewmodels.detail.CatViewModel
import com.example.foryourself.R
import com.example.foryourself.adapter.TypeAdapter
import com.example.foryourself.databinding.CatFragmentBinding
import com.example.foryourself.utils.LoadingDialog
import com.example.foryourself.utils.toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CatFragment : Fragment(), SearchView.OnQueryTextListener {
    private val args by navArgs<CatFragmentArgs>()
    private val binding: CatFragmentBinding by lazy {
        CatFragmentBinding.inflate(layoutInflater)
    }

    private val viewModel: CatViewModel by viewModels()
    private lateinit var typeAdapter: TypeAdapter
    private val loadingDialog: LoadingDialog by lazy(LazyThreadSafetyMode.NONE) {
        LoadingDialog(context = requireContext(), getString(R.string.loading_please_wait))
    }

    override fun onResume() {
        super.onResume()
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility =
            View.GONE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        typeAdapter = TypeAdapter()
        exclusiveAdapters()
        onClickItem()
        binding.tvCategoryCat.text = args.product
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.allOrderss(args.product).observe(viewLifecycleOwner) {
            Log.i("Res", it?.size.toString())
            typeAdapter.diffor.submitList(it)
        }
        viewModel.errorLiveData.observe(viewLifecycleOwner) { message -> toast(message) }
        viewModel.loadingLiveData.observe(viewLifecycleOwner) { status ->
            try {
                if (status) loadingDialog.show() else loadingDialog.dismiss()
            } catch (e: Exception) {
            }
        }
        binding.searchCat.setOnQueryTextListener(this)

    }

    private fun exclusiveAdapters() {
        binding.rvCategoriesCat.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            binding.rvCategoriesCat.adapter = typeAdapter
        }
    }

    private fun onClickItem() {
        typeAdapter.onItemClick_cate = { t ->
            toast(t.title.toString())
        }
    }

    override fun onQueryTextSubmit(searchText: String?): Boolean {
        if (searchText != null) {
            viewModel.searchProducts(searchText = searchText, args.product)
            viewModel.searchLiveData.observe(viewLifecycleOwner) {
                typeAdapter.diffor.submitList(it)
            }

        }
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) {
            val searchText = newText.lowercase()
            viewModel.searchProducts(searchText = searchText, args.product)
                .observe(viewLifecycleOwner) {
                    typeAdapter.diffor.submitList(it)
                }
        } else {
            viewModel.allOrderss(args.product).observe(viewLifecycleOwner) {
                typeAdapter.diffor.submitList(it)
            }
        }
        return false
    }


}