package com.example.foryourself.viewmodels.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.foryourself.data.retrofitResponse.getResponse.Result
import com.example.foryourself.data.retrofitResponse.postResponse.Result_2
import com.example.foryourself.repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class AddToViewModel @Inject constructor(
    private val repository: OrderRepository
) : ViewModel() {

    private var orderLiveData = MutableLiveData<Result>()

    fun addToProduct(result: Result_2) = liveData(context = viewModelScope.coroutineContext+ Dispatchers.IO){

        val response = repository.postOrders(result = result)
        if (response.isSuccessful){
            emit(response.body())
            Log.d("retryуву", response.body()!!.createdAt)
        }
        else{
            Log.d("retryуву", response.message().toString())
        }

    }

}