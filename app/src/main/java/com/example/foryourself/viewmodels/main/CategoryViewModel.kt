package com.example.foryourself.viewmodels.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.foryourself.data.retrofitResponse.order.getOrder.Result
import com.example.foryourself.db.ProductDao
import com.example.foryourself.db.model.ResultCache
import com.example.foryourself.utils.Mapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val dao: ProductDao,
    private val cascheToResultMapper: Mapper<ResultCache, Result>
) : ViewModel() {


    fun searchProductss(searchText: String) =
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            val result = dao.getProductsFromDATABASE()

            val searchProducts = mutableListOf<Result>()

            result.forEach {
                if (it.title!!.lowercase().contains(searchText.lowercase())) {
                    searchProducts.add(cascheToResultMapper.map(it))
                }
            }

            emit(searchProducts)
        }


}