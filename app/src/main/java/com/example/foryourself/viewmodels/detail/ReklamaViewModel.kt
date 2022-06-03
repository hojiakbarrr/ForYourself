package com.example.foryourself.viewmodels.detail

import android.util.Log
import androidx.lifecycle.*
import com.example.foryourself.data.retrofitResponse.order.getOrder.Result
import com.example.foryourself.data.retrofitResponse.reklama.updateReklama.UpdateReklama
import com.example.foryourself.db.ProductDao
import com.example.foryourself.db.model.ResultCache
import com.example.foryourself.repository.OrderRepository
import com.example.foryourself.utils.Mapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class ReklamaViewModel @Inject constructor(
    private val repository: OrderRepository,
    private val dao: ProductDao,
    private val resultToCascheMapper: Mapper<Result, ResultCache>,
    private val cascheToResultMapper: Mapper<ResultCache, Result>

) : ViewModel() {
    private var _orderLiveData = MutableLiveData<Result>()
    var orderLiveData: LiveData<Result> = _orderLiveData

    private var _loadingLiveData = MutableLiveData<Boolean>()
    var loadingLiveData: LiveData<Boolean> = _loadingLiveData

    private var _orderDeleteLiveData = MutableLiveData<String>()
    var orderDeleteLiveData: LiveData<String> = _orderDeleteLiveData


    fun updateReklama(id: String, reklama: UpdateReklama) = viewModelScope.launch(Dispatchers.IO) {
        _loadingLiveData.postValue(true)
        val response = repository.updateReklama(id, reklama)
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

    fun getReklama() = liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
        _loadingLiveData.postValue(true)

        val response = repository.getReklama()
        if (response.isSuccessful){
            emit(response.body()?.resultss)
            _loadingLiveData.postValue(false)
        }else{
            Log.d("tree", response.message())
        }
    }

}