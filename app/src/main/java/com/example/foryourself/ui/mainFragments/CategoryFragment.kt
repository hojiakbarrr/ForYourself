package com.example.foryourself.ui.mainFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
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
        var sliderView: SliderView
        val images = intArrayOf(
            R.drawable.reklama1,
            R.drawable.reklama2,
            R.drawable.reklama3,
        )

        val sliderAdapter = SliderAdapter(images)

        binding.imageSlider.apply {
            setSliderAdapter(sliderAdapter)
            setIndicatorAnimation(IndicatorAnimationType.WORM)
            setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION)
            startAutoCycle()
        }


    }
}