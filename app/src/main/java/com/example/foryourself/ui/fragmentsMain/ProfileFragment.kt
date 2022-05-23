package com.example.foryourself.ui.fragmentsMain

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.foryourself.R
import com.example.foryourself.databinding.ProfileFragmentBinding
import com.example.foryourself.utils.toast
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
        val type = ArrayList<String>() // Create image list
        val country = arrayOf("Ничего не выбрано","India", "Mumbai", "Faridabad", "Indonesia", "Africa")
        var aa: ArrayAdapter<*> = ArrayAdapter<Any?>(requireContext(), R.layout.drop_down_item, country)

        binding.filledExposed.setAdapter(aa)

        binding.filledExposed.setOnItemClickListener { adapterView, view, i, l ->
            toast(binding.filledExposed.text.toString())
        }


// imageList.add(SlideModel("String Url" or R.drawable)
// imageList.add(SlideModel("String Url" or R.drawable, "title") You can add title

//        imageList.add(SlideModel(R.drawable.reklama3, "The animal population decreased by 58 percent in 42 years.", scaleType = ScaleTypes.FIT))
//        imageList.add(SlideModel(R.drawable.reklama2, "Elephants and tigers may become extinct.",scaleType = ScaleTypes.FIT))
//        imageList.add(SlideModel(R.drawable.reklama1, scaleType = ScaleTypes.FIT))
//
//        binding.imageSlider.setImageList(imageList)
    }
}