package com.example.foryourself

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foryourself.data.retrofitResponse.getResponse.Result
import com.example.foryourself.data.retrofitResponse.postResponse.Result_2
import com.example.foryourself.data.retrofitResponse.putReklama.PuttReklama
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


    fun updateReklama(id: String, reklama: PuttReklama) = viewModelScope.launch(Dispatchers.IO) {
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

}