package com.example.foryourself.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foryourself.R
import com.example.foryourself.adapter.ExclusiveAdapter
import com.example.foryourself.databinding.CategoryFragmentBinding
import com.example.foryourself.databinding.HomeFragmentBinding
import com.example.foryourself.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val binding: HomeFragmentBinding by lazy {
        HomeFragmentBinding.inflate(layoutInflater)
    }
    private lateinit var exclusiveAdapter: ExclusiveAdapter
    private  val viewModel: HomeViewModel by viewModels()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exclusiveAdapter = ExclusiveAdapter()
        exclusiveAdapters()

    }


    private fun exclusiveAdapters() {
        binding.recExclusive.apply {
            layoutManager =  LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
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
        viewModel.allOrders().observe(viewLifecycleOwner){
            exclusiveAdapter.diffor.submitList(it)
        }
//        observeExclusive()
    }

//    private fun observeExclusive() {
//        viewModel.observeOrders().observe(requireActivity(), Observer { it ->
//
//            Log.d("Tryt", it.toString())
//        })
//    }
}