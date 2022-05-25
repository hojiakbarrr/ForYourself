package com.example.foryourself

import android.util.Log
import androidx.lifecycle.*
import com.example.foryourself.data.retrofitResponse.getResponse.Result
import com.example.foryourself.db.ProductDao
import com.example.foryourself.db.model.ResultCache
import com.example.foryourself.repository.OrderRepository
import com.example.foryourself.utils.Mapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject


@HiltViewModel
class CatViewModel @Inject constructor(
    private val repository: OrderRepository,
    private val dao: ProductDao,
    private val resultToCascheMapper: Mapper<Result, ResultCache>,
    private val cascheToResultMapper: Mapper<ResultCache, Result>

) : ViewModel() {

    private var _orderLiveData = MutableLiveData<List<Result>>()
    private var _searchLiveData = MutableLiveData<List<Result>>()

    var searchLiveData: LiveData<List<Result>> = _searchLiveData

    private var _loadingLiveData = MutableLiveData<Boolean>()
    var loadingLiveData: LiveData<Boolean> = _loadingLiveData


    private var _errorLiveData = MutableLiveData<String>()
    var errorLiveData: LiveData<String> = _errorLiveData


    fun allOrderss(word: String) =
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            _loadingLiveData.postValue(true)
            val searchProducts = mutableListOf<Result>()

            if (dao.getProductsFromDATABASE().isEmpty()) {
                val response = repository.getOrders()
                if (response.isSuccessful) {
                    Log.d("tt", dao.getProductsFromDATABASE().size.toString())
                    response.body()!!.results?.forEach { product ->
                        dao.addProductsFromService(resultToCascheMapper.map(product))
                    }
                }
                val resuk = dao.getcategory(word)
                emit(resuk?.map {
                    cascheToResultMapper.map(it)
                })

                _loadingLiveData.postValue(false)
            } else {
                val resuk = dao.getcategory(word)
                emit(resuk?.map {
                    cascheToResultMapper.map(it)
                })


                _loadingLiveData.postValue(false)
            }
        }

    fun searchProducts(searchText: String, type: String) =
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {

            val resuk = dao.getcategory(type)

            val searchProducts = mutableListOf<Result>()

            resuk?.forEach {
                if (it.title!!.lowercase().contains(searchText.lowercase())) {
                    searchProducts.add(cascheToResultMapper.map(it))
                }
            }

            emit(searchProducts)
        }

}