package com.example.foryourself.viewmodels

import androidx.lifecycle.*
import com.example.foryourself.data.retrofitResponse.getResponse.Result
import com.example.foryourself.repository.OrderRepository
import com.example.foryourself.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(

    private val repository: OrderRepository

) : ViewModel() {
    // TODO: Implement the ViewModel
    private var _orderLiveData = MutableLiveData<List<Result>>()
    var orderLiveData: LiveData<List<Result>> = _orderLiveData

    private var _loadingLiveData = MutableLiveData<Boolean>()
    var loadingLiveData: LiveData<Boolean> = _loadingLiveData


    private var _errorLiveData = MutableLiveData<String>()
    var errorLiveData: LiveData<String> = _errorLiveData


//    fun allOrders() = liveData(context = viewModelScope.coroutineContext+Dispatchers.IO){
//        val response = repository.getOrders()
//        if (response.isSuccessful){
//               emit(response.body()!!.results)
//            Log.d("retryуву", response.body().toString()
//        }
//    }

    fun getOrders() = viewModelScope.launch {
        repository.fetchOrders().collectLatest { resourse ->
            when (resourse.status) {
                Status.SUCCESS -> {
                    _orderLiveData.value = resourse.data!!
                    _loadingLiveData.value = false
                }
                Status.LOADING -> {
                    _loadingLiveData.value = true
                }
                Status.ERROR -> {
                    _loadingLiveData.value = false
                    _errorLiveData.value = resourse.message!!
                }
                Status.EMPTY ->{

                }
            }
        }
    }


    fun observeOrders(): LiveData<List<Result>> {
        return _orderLiveData
    }

}