package com.example.foryourself.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.example.foryourself.R
import com.example.foryourself.databinding.ActivityDetailBinding
import com.example.foryourself.databinding.CategoryFragmentBinding
import com.example.foryourself.viewmodels.CategoryViewModel
import com.example.foryourself.viewmodels.Detail_viewmodel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private val binding: ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }
    private  val viewModel: Detail_viewmodel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}