package com.example.foryourself.ui.fragmentsMain

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foryourself.R
import com.example.foryourself.adapter.FavoritesAdapter
import com.example.foryourself.adapter.TypeAdapter
import com.example.foryourself.databinding.FavoritesFragmentBinding
import com.example.foryourself.databinding.TypeFragmentBinding
import com.example.foryourself.utils.LoadingDialog
import com.example.foryourself.utils.toast
import com.example.foryourself.viewmodels.main.FavoritesViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment() {
    private val binding: FavoritesFragmentBinding by lazy {
        FavoritesFragmentBinding.inflate(layoutInflater)
    }

    private val viewModel: FavoritesViewModel by viewModels()
    private lateinit var favoritesAdapter: FavoritesAdapter
    private val loadingDialog: LoadingDialog by lazy(LazyThreadSafetyMode.NONE) {
        LoadingDialog(context = requireContext(), getString(R.string.loading_please_wait))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favoritesAdapter = FavoritesAdapter()
        preparadapter()
    }

    override fun onResume() {
        super.onResume()
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility =
            View.VISIBLE
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        onClickItem()



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getOneOrder()
        viewModel.orderLiveData.observe(viewLifecycleOwner) {
            favoritesAdapter.diffor.submitList(it)
        }


    }

    private fun preparadapter() {
        binding.recFavor.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            binding.recFavor.adapter = favoritesAdapter
        }
    }

    private fun onClickItem() {
        favoritesAdapter.onItemClicked = { t ->
            toast(t.title.toString())
        }
    }

}