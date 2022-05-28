package com.example.foryourself.ui.fragmentsMain

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.foryourself.viewmodels.main.FavoritesViewModel
import com.example.foryourself.R
import com.example.foryourself.databinding.CategoryFragmentBinding
import com.example.foryourself.databinding.FavoritesFragmentBinding
import com.example.foryourself.viewmodels.main.CategoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment() {
    private val binding: FavoritesFragmentBinding by lazy {
        FavoritesFragmentBinding.inflate(layoutInflater)
    }
    private val viewModel: FavoritesViewModel by viewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }


}