package com.example.foryourself.ui.mainFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.foryourself.databinding.SendToFragmentBinding
import com.example.foryourself.viewmodels.main.SendToViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SendToFragment : Fragment() {
    private val binding: SendToFragmentBinding by lazy {
        SendToFragmentBinding.inflate(layoutInflater)
    }

    private  val viewModel: SendToViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

}