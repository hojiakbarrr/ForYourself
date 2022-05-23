package com.example.foryourself

import androidx.lifecycle.ViewModel
import com.example.foryourself.db.ProductDao
import com.example.foryourself.repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class TypeViewModel  @Inject constructor(
    private val repository: OrderRepository,
    private val dao: ProductDao,
    ) : ViewModel() {




}