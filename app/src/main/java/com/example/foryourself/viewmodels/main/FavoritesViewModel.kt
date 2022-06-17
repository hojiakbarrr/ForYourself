package com.example.foryourself.viewmodels.main

import android.util.Log
import androidx.lifecycle.*
import com.example.foryourself.data.retrofitResponse.order.getOrder.Result
import com.example.foryourself.data.retrofitResponse.userOrders.postUserOrders.PostUserOrders
import com.example.foryourself.data.retrofitResponse.users.postUser.PutUsers
import com.example.foryourself.db.ProductDao
import com.example.foryourself.db.model.FavoritesCache
import com.example.foryourself.repository.OrderRepository
import com.example.foryourself.utils.Mapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val dao: ProductDao,
    private val mapper: Mapper<FavoritesCache, Result>,
    private val mapper2: Mapper<Result, FavoritesCache>,
    private val repository: OrderRepository,

    ) : ViewModel() {
    private var _orderLiveData = MutableLiveData<List<FavoritesCache>>()
    var orderLiveData: LiveData<List<FavoritesCache>> = _orderLiveData

    fun getOneOrder() = viewModelScope.launch {
        val rr = dao.getFavorites()
        _orderLiveData.value = rr
    }

    fun deleteOrder(product: FavoritesCache) = viewModelScope.launch {
        dao.deleteFrom(product)
        repository.deleteuserOrders(product.objectId)
    }

    fun favoritesUserOrders() =
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {

            var response = repository.getuserOrders()
            if (response.isSuccessful) {

                response.body()!!.ressults.forEach {
                    if (dao.getUsers()[0].objectId == it.argument) dao.addUsersOrder(it)
                    emit(dao.getFavorites())
                }
                Log.d("response", response.body()?.ressults.toString())
            }
        }

    fun userOrder(googleEmail: String, googlename: String) = viewModelScope.launch {

        val rr = repository.getuserOrders()
        if (rr.isSuccessful) {
            rr.body()!!.ressults.forEach {
                if (dao.getUsers()[0].objectId == it.argument) dao.addUsersOrder(it)
            }
        }

        fun observeOrders(): LiveData<List<FavoritesCache>> {
            return _orderLiveData
        }

    }


}