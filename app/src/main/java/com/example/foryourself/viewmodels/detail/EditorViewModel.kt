package com.example.foryourself.viewmodels.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foryourself.data.retrofitResponse.order.getOrder.Result
import com.example.foryourself.data.retrofitResponse.order.postOrder.Result_2
import com.example.foryourself.db.ProductDao
import com.example.foryourself.db.model.ResultCache
import com.example.foryourself.repository.OrderRepository
import com.example.foryourself.utils.Mapper
import com.example.foryourself.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
    var orderDeleteLiveData: LiveData<String> = _orderDeleteLiveData

    private var _loadingLiveData = MutableLiveData<Boolean>()
    var loadingLiveData: LiveData<Boolean> = _loadingLiveData

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
        dao.clearTable()
        repository.getOrders()
    }

    fun updateOrder(id: String, result: Result_2) = viewModelScope.launch(Dispatchers.IO) {
        _loadingLiveData.postValue(true)
        val response = repository.updateOrder(id = id, result = result)
        if (response.isSuccessful) {
            withContext(Dispatchers.Main) {
                _orderDeleteLiveData.value = "Товар был успешно обновлен"
            }
            _loadingLiveData.postValue(false)
        } else withContext(Dispatchers.Main) {
            _orderDeleteLiveData.value = "Неполучилось обновить товар"
            _loadingLiveData.postValue(false)
        }
    }


}