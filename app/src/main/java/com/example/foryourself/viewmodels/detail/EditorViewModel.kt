package com.example.foryourself.viewmodels.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foryourself.data.retrofitResponse.getResponse.Result
import com.example.foryourself.data.retrofitResponse.postResponse.Result_2
import com.example.foryourself.db.ProductDao
import com.example.foryourself.db.model.ResultCache
import com.example.foryourself.repository.OrderRepository
import com.example.foryourself.utils.Mapper
import com.example.foryourself.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class EditorViewModel @Inject constructor(

    private val repository: OrderRepository,
    private val dao: ProductDao,
    private val resultToCascheMapper: Mapper<Result, ResultCache>,

    ) : ViewModel() {
    private var _orderLiveData = MutableLiveData<Result>()
    var orderLiveData: LiveData<Result> = _orderLiveData
    private var _orderDeleteLiveData = MutableLiveData<String>()


    fun getOneOrder(id: String) = viewModelScope.launch {
        repository.fetchOneOrder(id).collectLatest { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    _orderLiveData.value = resource.data!!
                }
            }
        }
    }

    fun ss() = viewModelScope.launch {
        val ss = repository.getOrders()
    }

    fun updateOrder(id: String, result: Result_2) = viewModelScope.launch {
        val response = repository.updateOrder(id = id, result = result)
        if (response.isSuccessful) {
            _orderDeleteLiveData.value = "Товар был успешно обновлен"
        } else _orderDeleteLiveData.value = "Неполучилось обновить товар"
    }

    fun updateDATABASE(product: Result) = viewModelScope.launch {
        val dao = repository.updateinBASE(product = resultToCascheMapper.map(product))

    }

    fun observeUpdateOrder(): LiveData<String> {
        return _orderDeleteLiveData
    }
}