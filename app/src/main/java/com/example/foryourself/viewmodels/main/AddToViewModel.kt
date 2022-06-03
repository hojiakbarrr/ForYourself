package com.example.foryourself.viewmodels.main

import android.util.Log
import androidx.lifecycle.*
import com.example.foryourself.data.retrofitResponse.order.getOrder.Result
import com.example.foryourself.data.retrofitResponse.order.postOrder.Result_2
import com.example.foryourself.db.ProductDao
import com.example.foryourself.repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddToViewModel @Inject constructor(
    private val repository: OrderRepository,
    private val dao: ProductDao,
) : ViewModel() {

    private var orderLiveData = MutableLiveData<Result>()

    private var _loadingLiveData = MutableLiveData<Boolean>()
    var loadingLiveData: LiveData<Boolean> = _loadingLiveData

    fun addToProduct(result: Result_2) =
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {

            val response = repository.postOrders(result = result)
            if (response.isSuccessful) {
                emit(response.body())
                _loadingLiveData.postValue(false)
                Log.d("retryуву", response.body()!!.createdAt!!)
            } else {
                Log.d("retryуву", response.message().toString())
            }
        }

    fun ss() = viewModelScope.launch {
        dao.clearTable()
        repository.getOrders()

    }


}