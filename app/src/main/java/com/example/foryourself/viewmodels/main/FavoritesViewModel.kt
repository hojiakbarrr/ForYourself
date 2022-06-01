package com.example.foryourself.viewmodels.main

import androidx.lifecycle.*
import com.example.foryourself.data.retrofitResponse.getResponse.Result
import com.example.foryourself.db.ProductDao
import com.example.foryourself.db.model.FavoritesCache
import com.example.foryourself.db.model.ResultCache
import com.example.foryourself.repository.OrderRepository
import com.example.foryourself.utils.Mapper
import com.example.foryourself.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val dao: ProductDao,
    private val mapper: Mapper<FavoritesCache, Result>,
    private val repository: OrderRepository,

    ) : ViewModel() {
    private var _orderLiveData = MutableLiveData<List<Result>>()
    var orderLiveData: LiveData<List<Result>> = _orderLiveData

    fun getOneOrder()  = viewModelScope.launch  {
        val rr = dao.getFavorites()
        _orderLiveData.value = rr.map {
            mapper.map(it)
        }
    }

}