package com.example.foryourself.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.example.foryourself.data.retrofitResponse.Result
import com.example.foryourself.repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(

    private val repository: OrderRepository

) : ViewModel() {
    // TODO: Implement the ViewModel
    private var orderLiveData = MutableLiveData<List<Result>>()


    fun allOrders() = liveData(context = viewModelScope.coroutineContext+Dispatchers.IO){

        val response = repository.getOrders()

        if (response.isSuccessful){
               emit(response.body()!!.results)
            Log.d("retryуву", response.body().toString())

        }
    }





    fun observeOrders(): MutableLiveData<List<Result>> {
        return orderLiveData
    }

}