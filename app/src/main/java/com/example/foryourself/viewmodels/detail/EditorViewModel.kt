package com.example.foryourself.viewmodels.detail

import androidx.lifecycle.ViewModel
import com.example.foryourself.db.ProductDao
import com.example.foryourself.repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class EditorViewModel @Inject constructor(

    private val repository: OrderRepository,
    private val dao: ProductDao,

    ): ViewModel() {
    // TODO: Implement the ViewModel
}