package com.example.foryourself.viewmodels.main

import androidx.lifecycle.ViewModel
import com.example.foryourself.data.retrofitResponse.getResponse.Result
import com.example.foryourself.db.ProductDao
import com.example.foryourself.db.model.ResultCache
import com.example.foryourself.utils.Mapper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel  @Inject constructor(
    private val dao: ProductDao,
    private val cascheToResultMapper: Mapper<ResultCache, Result>
): ViewModel() {
    // TODO: Implement the ViewModel
}