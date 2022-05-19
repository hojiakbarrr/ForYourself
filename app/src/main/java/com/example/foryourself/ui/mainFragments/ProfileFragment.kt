package com.example.foryourself.ui.mainFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.foryourself.R
import com.example.foryourself.databinding.ProfileFragmentBinding
import com.example.foryourself.viewmodels.main.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private val binding: ProfileFragmentBinding by lazy {
        ProfileFragmentBinding.inflate(layoutInflater)
    }

    private  val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageList = ArrayList<SlideModel>() // Create image list

// imageList.add(SlideModel("String Url" or R.drawable)
// imageList.add(SlideModel("String Url" or R.drawable, "title") You can add title

//        imageList.add(SlideModel(R.drawable.reklama3, "The animal population decreased by 58 percent in 42 years.", scaleType = ScaleTypes.FIT))
//        imageList.add(SlideModel(R.drawable.reklama2, "Elephants and tigers may become extinct.",scaleType = ScaleTypes.FIT))
//        imageList.add(SlideModel(R.drawable.reklama1, scaleType = ScaleTypes.FIT))
//
//        binding.imageSlider.setImageList(imageList)
    }
}