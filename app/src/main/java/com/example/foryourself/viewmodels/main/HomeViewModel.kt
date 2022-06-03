package com.example.foryourself.viewmodels.main

import android.util.Log
import androidx.lifecycle.*
import com.example.foryourself.data.retrofitResponse.order.getOrder.Result
import com.example.foryourself.db.ProductDao
import com.example.foryourself.db.model.FavoritesCache
import com.example.foryourself.db.model.ResultCache
import com.example.foryourself.repository.OrderRepository
import com.example.foryourself.utils.Mapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dao: ProductDao,
    private val repository: OrderRepository,
    private val resultToCascheMapper: Mapper<Result, ResultCache>,

    private val resultToFavoritCascheMapper: Mapper<Result, FavoritesCache>,

    private val cascheToResultMapper: Mapper<ResultCache, Result>

) : ViewModel() {
    // TODO: Implement the ViewModel
    private var _orderLiveData = MutableLiveData<List<Result>>()
    var orderLiveData: LiveData<List<Result>> = _orderLiveData
    private var _loadingLiveData = MutableLiveData<Boolean>()
    var loadingLiveData: LiveData<Boolean> = _loadingLiveData


    private var _errorLiveData = MutableLiveData<String>()
    var errorLiveData: LiveData<String> = _errorLiveData


    fun addToFav(product : Result) = viewModelScope.launch {
        repository.addtoFav(product = resultToFavoritCascheMapper.map(product))
        Log.d("dfgdfg",dao.getFavorites().toString())
    }

    fun allOrders() = liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
        _loadingLiveData.postValue(true)


        if (dao.getProductsFromDATABASE().isEmpty()) {
            val response = repository.getOrders()
            if (response.isSuccessful) {
                response.body()!!.results?.forEach { product ->
                    dao.addProductsFromService(resultToCascheMapper.map(product))
                }
            }
            val resuk = dao.getTipy("Эксклюзив")
            emit(resuk?.map {
                cascheToResultMapper.map(it)
            })

            withContext(context = Dispatchers.Main) {
                val rr = dao.getTipy("Бестселлер")
                _orderLiveData.value = rr?.map {
                    cascheToResultMapper.map(it)
                }
            }

            _loadingLiveData.postValue(false)
        } else {
            val resuk = dao.getTipy("Эксклюзив")
            emit(resuk?.map { cascheToResultMapper.map(it) })

            withContext(context = Dispatchers.Main) {
                val rr = dao.getTipy("Бестселлер")
                _orderLiveData.value = rr?.map { cascheToResultMapper.map(it) }
            }
            _loadingLiveData.postValue(false)
        }

    }


    fun allOrdersREFRESH() = liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
        _loadingLiveData.postValue(true)
        dao.clearTable()
        val response = repository.getOrders()
        if (response.isSuccessful) {
            response.body()!!.results?.forEach { product ->
                dao.addProductsFromService(resultToCascheMapper.map(product))
            }
        }
        val resuk = dao.getTipy("Эксклюзив")
        emit(resuk?.map {
            cascheToResultMapper.map(it)
        })

        withContext(context = Dispatchers.Main) {
            val rr = dao.getTipy("Бестселлер")
            _orderLiveData.value = rr?.map {
                cascheToResultMapper.map(it)
            }
        }

        _loadingLiveData.postValue(false)

    }

    fun getReklama() = liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
        _loadingLiveData.postValue(true)
        if (dao.getreklamaFromDATABASE().isEmpty()){
            val response = repository.getReklama()
            if (response.isSuccessful) {
                response.body()!!.resultss.forEach {
                    dao.addReklama(it)
                }
                emit(dao.getreklamaFromDATABASE())
                Log.d("ttyuyuy", dao.getreklamaFromDATABASE().size.toString())
                _loadingLiveData.postValue(false)
            } else {
                Log.d("tree", response.message())
            }
        }else{
            emit(dao.getreklamaFromDATABASE())
            _loadingLiveData.postValue(false)
        }
    }


    fun observeOrders(): LiveData<List<Result>> {
        return _orderLiveData
    }

}