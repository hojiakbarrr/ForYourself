package com.example.foryourself.viewmodels

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foryourself.R
import com.example.foryourself.data.retrofitResponse.Result
import com.example.foryourself.db.ProductDao
import com.example.foryourself.repository.OrderRepository
import com.example.foryourself.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class Detail_viewmodel  @Inject constructor(

    private val repository: OrderRepository,
    private val dao: ProductDao,


    ) : ViewModel() {

    private var _orderLiveData = MutableLiveData<Result>()
    var orderLiveData: LiveData<Result> = _orderLiveData


    fun getOneOrder(id : String) = viewModelScope.launch {
        repository.fetchOneOrder(id).collectLatest { resource ->
            when (resource.status){
                Status.SUCCESS -> {
                    _orderLiveData.value = resource.data!!
                }
            }
        }
    }

}