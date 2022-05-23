package com.example.foryourself

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.foryourself.databinding.EditorrFragmentBinding
import com.example.foryourself.databinding.TypeFragmentBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TypeFragment : Fragment() {

    private val binding: TypeFragmentBinding by lazy {
        TypeFragmentBinding.inflate(layoutInflater)
    }
    private  val viewModel: TypeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }



}