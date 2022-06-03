package com.example.foryourself.viewmodels.detail

import android.util.Log
import androidx.lifecycle.*
import com.example.foryourself.data.retrofitResponse.order.getOrder.Result
import com.example.foryourself.db.ProductDao
import com.example.foryourself.db.model.FavoritesCache
import com.example.foryourself.db.model.ResultCache
import com.example.foryourself.repository.OrderRepository
import com.example.foryourself.utils.Mapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject


@HiltViewModel
class TypeViewModel @Inject constructor(
    private val repository: OrderRepository,
    private val dao: ProductDao,
    private val resultToCascheMapper: Mapper<Result, ResultCache>,
    private val cascheToResultMapper: Mapper<ResultCache, Result>,
    private val resultToFavoritCascheMapper: Mapper<Result, FavoritesCache>,

    ) : ViewModel() {



    private var _searchLiveData = MutableLiveData<List<Result>>()
    var searchLiveData: LiveData<List<Result>> = _searchLiveData

    private var _loadingLiveData = MutableLiveData<Boolean>()
    var loadingLiveData: LiveData<Boolean> = _loadingLiveData


    private var _errorLiveData = MutableLiveData<String>()
    var errorLiveData: LiveData<String> = _errorLiveData


    fun allOrders(word: String) =
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            _loadingLiveData.postValue(true)

            val resuk = dao.getTipy(word)
                emit(resuk?.map {
                    cascheToResultMapper.map(it)
                })
            _loadingLiveData.postValue(false)
        }

    fun searchProduct(searchText: String, type: String) =
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {

            val resuk = dao.getTipy(type)

            val searchProducts = mutableListOf<Result>()

            resuk?.forEach {
                if (it.title!!.lowercase().contains(searchText.lowercase())) {
                    searchProducts.add(cascheToResultMapper.map(it))
                }
            }
            emit(searchProducts)

        }

    fun addToFav(product : Result) = viewModelScope.launch {
        repository.addtoFav(product = resultToFavoritCascheMapper.map(product))
        Log.d("dfgdfg",dao.getFavorites().toString())
    }


}