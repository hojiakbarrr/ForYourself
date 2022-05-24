package com.example.foryourself.viewmodels.detail

import android.util.Log
import androidx.lifecycle.*
import com.example.foryourself.data.retrofitResponse.getResponse.Result
import com.example.foryourself.db.ProductDao
import com.example.foryourself.db.model.ResultCache
import com.example.foryourself.repository.OrderRepository
import com.example.foryourself.utils.Mapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class TypeViewModel  @Inject constructor(
    private val repository: OrderRepository,
    private val dao: ProductDao,
    private val resultToCascheMapper: Mapper<Result, ResultCache>,
    private val cascheToResultMapper: Mapper<ResultCache, Result>

) : ViewModel() {

    private var _orderLiveData = MutableLiveData<List<Result>>()
    var orderLiveData: LiveData<List<Result>> = _orderLiveData

    private var _loadingLiveData = MutableLiveData<Boolean>()
    var loadingLiveData: LiveData<Boolean> = _loadingLiveData


    private var _errorLiveData = MutableLiveData<String>()
    var errorLiveData: LiveData<String> = _errorLiveData



    fun allOrders(word: String) = liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
        _loadingLiveData.postValue(true)

        if (dao.getProductsFromDATABASE().isEmpty()) {
            val response = repository.getOrders()
            if (response.isSuccessful) {
                Log.d("tt", dao.getProductsFromDATABASE().size.toString())
                response.body()!!.results?.forEach { product ->
                    dao.addProductsFromService(resultToCascheMapper.map(product))
                }
            }
            val resuk = dao.getTipy(word)
            emit(resuk?.map {
                cascheToResultMapper.map(it)
            })

            _loadingLiveData.postValue(false)
        } else {
            val resuk = dao.getTipy(word)
            emit(resuk?.map {
                cascheToResultMapper.map(it)
            })
            _loadingLiveData.postValue(false)
        }
    }

}