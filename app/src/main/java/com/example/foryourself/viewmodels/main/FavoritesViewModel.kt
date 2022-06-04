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
    private val mapper2: Mapper<Result,FavoritesCache>,
    private val repository: OrderRepository,

    ) : ViewModel() {
    private var _orderLiveData = MutableLiveData<List<Result>>()
    var orderLiveData: LiveData<List<Result>> = _orderLiveData

    fun getOneOrder()  = viewModelScope.launch  {
        val rr = dao.getFavorites()
        _orderLiveData.value = rr.map {
            mapper.map(it)
        }
    }

    fun deleteOrder(product : Result)  = viewModelScope.launch  {
        val rr = dao.deleteFrom(mapper2.map(product))
    }

    fun favoritesUserOrders() = liveData(context = viewModelScope.coroutineContext + Dispatchers.IO){

        var response = repository.getuserOrders()
        if (response.isSuccessful){
            response.body()?.let { emit(it.ressults) }
            Log.d("response", response.body()?.ressults.toString())
        }

    }

    fun userOrder(googleEmail: String, googlename: String) = viewModelScope.launch {
        Log.d("namewww", googlename + googleEmail)
        var bb: com.example.foryourself.data.retrofitResponse.users.getUsers.ResultUserdata? = null
        var cc: com.example.foryourself.data.retrofitResponse.userOrders.getUserOrders.Result? =
            null

        val rr = repository.getuserOrders()
        if (rr.isSuccessful){
            rr.body()!!.ressults.forEach {
            }
        }


        val response = repository.getUser()
        if (response.isSuccessful) {
            response.body()?.rrresults?.forEach { r ->
                bb = r
            }

            if (bb!!.email != googleEmail && bb!!.name != googlename) {

                repository.postuserOrders(
                    userOrders = PostUserOrders(
                        email = googleEmail,
                        name = googlename
                    )
                )
                repository.postUser(
                    user = PutUsers(
                        email = googleEmail,
                        name = googlename
                    )
                )
            }else{

            }


        }


        fun observeOrders(): LiveData<List<Result>> {
            return _orderLiveData
        }

    }


}