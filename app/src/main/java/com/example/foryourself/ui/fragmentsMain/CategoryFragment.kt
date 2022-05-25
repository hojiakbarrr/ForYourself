package com.example.foryourself.ui.fragmentsMain

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foryourself.R
import com.example.foryourself.adapter.TypeAdapter
import com.example.foryourself.databinding.CategoryFragmentBinding
import com.example.foryourself.utils.toast
import com.example.foryourself.viewmodels.main.CategoryViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CategoryFragment : Fragment(), SearchView.OnQueryTextListener {
    private val binding: CategoryFragmentBinding by lazy {
        CategoryFragmentBinding.inflate(layoutInflater)
    }
    private lateinit var typeAdapter: TypeAdapter


    private val viewModel: CategoryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        typeAdapter = TypeAdapter()
        exclusiveAdapters()
        onClickItem()

        return binding.root
    }

    private fun exclusiveAdapters() {
        binding.recCategoriyy.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            binding.recCategoriyy.adapter = typeAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility =
            View.VISIBLE
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.uybka.setOnClickListener {
            val action = CategoryFragmentDirections.actionCategoryFragment2ToCatFragment("Юбки")
            Navigation.findNavController(it).navigate(action)
        }
        binding.searchCategory.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(searchText: String?): Boolean {

        binding.apply {
            recCategoriyy.visibility = View.GONE
            allItem.visibility = View.VISIBLE
        }
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null && newText.isNotBlank()) {
            binding.apply {
                recCategoriyy.visibility = View.VISIBLE
                allItem.visibility = View.GONE
            }
            val searchText = newText.lowercase()
            viewModel.searchProductss(searchText = searchText)
                .observe(viewLifecycleOwner) {
                    typeAdapter.diffor.submitList(it)
                }
        } else  {
            binding.apply {
                recCategoriyy.visibility = View.GONE
                allItem.visibility = View.VISIBLE
            }
            Log.i("Resуу", "sgdgssd")

        }
        return false
    }

    private fun onClickItem() {
        typeAdapter.onItemClick_cate = { t ->
            toast(t.title.toString())
        }
    }
}