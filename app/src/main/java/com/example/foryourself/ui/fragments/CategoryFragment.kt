package com.example.foryourself.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.foryourself.R
import com.example.foryourself.adapter.SliderAdapter
import com.example.foryourself.databinding.CategoryFragmentBinding
import com.example.foryourself.viewmodels.main.CategoryViewModel
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CategoryFragment : Fragment() {
    private val binding: CategoryFragmentBinding by lazy {
        CategoryFragmentBinding.inflate(layoutInflater)
    }



    private  val viewModel: CategoryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}