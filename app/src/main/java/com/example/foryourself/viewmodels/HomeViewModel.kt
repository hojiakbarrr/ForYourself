package com.example.foryourself.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.example.foryourself.data.Result
import com.example.foryourself.data.TestResponse
import com.example.foryourself.data.retrofitResponse.OrdersResponse
import com.example.foryourself.data.types.TypeProduct
import com.example.foryourself.repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(

    private val repository: OrderRepository

) : ViewModel() {
    // TODO: Implement the ViewModel
    private var orderLiveData = MutableLiveData<List<Result>>()


    fun allOrders() = liveData(context = viewModelScope.coroutineContext+Dispatchers.IO){

        var response = repository.getOrders()

        if (response.isSuccessful){
               emit(response.body()!!.results)
            Log.d("retryуву", response.body().toString())

        }
    }





    fun observeOrders(): MutableLiveData<List<Result>> {
        return orderLiveData
    }

}