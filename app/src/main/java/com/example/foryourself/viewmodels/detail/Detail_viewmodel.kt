package com.example.foryourself.viewmodels.detail

import android.util.Log
import androidx.lifecycle.*
import com.example.foryourself.data.retrofitResponse.getResponse.Result
import com.example.foryourself.db.ProductDao
import com.example.foryourself.db.model.ResultCache
import com.example.foryourself.repository.OrderRepository
import com.example.foryourself.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
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

    private var _orderDeleteLiveData = MutableLiveData<String>()


    fun deleteOrderBASE(id: String) = viewModelScope.launch {
        repository.deleteOrderInBASE(id)
    }

    fun deleteOrder(id : String) = viewModelScope.launch {
        val response = repository.deleteOrderInServer(id)
        if (response.isSuccessful){
            _orderDeleteLiveData.value = "Товар был успешно удален"
        }
    }



    fun getOneOrder(id : String) = viewModelScope.launch {
        repository.fetchOneOrder(id).collectLatest { resource ->
            when (resource.status){
                Status.SUCCESS -> {
                    _orderLiveData.value = resource.data!!
                }
            }
        }
    }

    fun observeDeleteOrder(): LiveData<String> {
        return _orderDeleteLiveData
    }

}