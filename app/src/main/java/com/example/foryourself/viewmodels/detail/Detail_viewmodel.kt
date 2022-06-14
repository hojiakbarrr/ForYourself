package com.example.foryourself.viewmodels.detail

import android.util.Log
import androidx.lifecycle.*
import com.example.foryourself.data.retrofitResponse.order.getOrder.Result
import com.example.foryourself.data.retrofitResponse.userOrders.getUserOrders.ResultUsersOrder
import com.example.foryourself.data.retrofitResponse.userOrders.postUserOrders.PostUserOrders
import com.example.foryourself.db.ProductDao
import com.example.foryourself.db.model.FavoritesCache
import com.example.foryourself.repository.OrderRepository
import com.example.foryourself.utils.Mapper
import com.example.foryourself.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class Detail_viewmodel @Inject constructor(
    private val repository: OrderRepository,
    private val dao: ProductDao,
    private val resultToCascheMapper: Mapper<Result, FavoritesCache>,
    private val cascheToResultMapper: Mapper<FavoritesCache, Result>

) : ViewModel() {

    private var _orderLiveData = MutableLiveData<Result>()
    var orderLiveData: LiveData<Result> = _orderLiveData

    private var _orderDeleteLiveData = MutableLiveData<String>()

    fun addToFav(product: Result, googleEmail: String, googlename: String, amount: String) =
        viewModelScope.launch {
//        repository.addtoFav(product = resultToCascheMapper.map(product))
            var usersOrder: ResultUsersOrder? = null

            val response = repository.getuserOrders()
            if (response.isSuccessful) {
                response.body()!!.ressults.forEach {
                    usersOrder = it
                }
            }

            val err = dao.getUsers()[0].objectId + product.title
            val ersp = dao.getUsers()[0].name + product.title

            Log.d("qwertyui", err)

            if (err != usersOrder!!.argument  && ersp != usersOrder!!.argument2) {

                repository.postuserOrders(
                    userOrders = PostUserOrders(
                        email = googleEmail,
                        name = googlename,
                        amount = amount,
                        title = product.title,
                        colors1 = product.colors1,
                        colors2 = product.colors2,
                        seventhSize = product.seventhSize,
                        colors3 = product.colors3,
                        image_third = product.image_third,
                        season = product.season,
                        thirdSize = product.thirdSize,
                        price = product.price,
                        image_first = product.image_first,
                        youtubeTrailer = product.youtubeTrailer,
                        colors = product.colors,
                        firstSize = product.firstSize,
                        secondSize = product.secondSize,
                        sixthSize = product.sixthSize,
                        fifthSize = product.fifthSize,
                        eighthSize = product.eighthSize,
                        fourthSize = product.fourthSize,
                        description = product.description,
                        image_main = product.image_main,
                        category = product.category,
                        tipy = product.tipy,
                        argument = err,
                    )
                )
                _orderDeleteLiveData.value = "${product.title!!} добавлен в избранные"
            }else _orderDeleteLiveData.value = "${product.title!!} был добавлен в избранные"
        }


    fun deleteOrderBASE(id: String) = viewModelScope.launch {
        repository.deleteOrderInBASE(id)
    }

    fun deleteOrder(id: String) = viewModelScope.launch {
        val response = repository.deleteOrderInServer(id)
        if (response.isSuccessful) {
            _orderDeleteLiveData.value = "Товар был успешно удален"
        }
    }


    fun getOneOrder(id: String) = viewModelScope.launch {
        repository.fetchOneOrder(id).collectLatest { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    _orderLiveData.value = resource.data!!
                }
            }
        }
    }

    fun observeDeleteOrder(): LiveData<String> {
        return _orderDeleteLiveData
    }

    val message: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

}