package com.example.foryourself.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.foryourself.R
import com.example.foryourself.databinding.ActivityDetailBinding
import com.example.foryourself.databinding.ActivityEditorBinding
import com.example.foryourself.viewmodels.detail.Detail_viewmodel
import com.example.foryourself.viewmodels.detail.EditorViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditorActivity : AppCompatActivity() {
    private val binding: ActivityEditorBinding by lazy {
        ActivityEditorBinding.inflate(layoutInflater)
    }
    private val viewModel: EditorViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}