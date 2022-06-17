package com.example.foryourself.viewmodels.detail

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.example.foryourself.SharedPreferences
import com.example.foryourself.data.retrofitResponse.order.getOrder.Result
import com.example.foryourself.data.retrofitResponse.userOrders.getUserOrders.ResultUsersOrder
import com.example.foryourself.data.retrofitResponse.userOrders.postUserOrders.PostUserOrders
import com.example.foryourself.db.ProductDao
import com.example.foryourself.db.model.FavoritesCache
import com.example.foryourself.db.model.ResultCache
import com.example.foryourself.repository.OrderRepository
import com.example.foryourself.utils.Mapper
import com.example.foryourself.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class Detail_viewmodel @Inject constructor(
    private val repository: OrderRepository,
    private val dao: ProductDao,
    private val resultToCascheMapper: Mapper<Result, ResultCache>,
    private val cascheToResultMapper: Mapper<Result, FavoritesCache>

) : ViewModel() {

    private var _orderLiveData = MutableLiveData<Result>()
    var orderLiveData: LiveData<Result> = _orderLiveData

    private var _orderDeleteLiveData = MutableLiveData<String>()

    private var _loadingLiveData = MutableLiveData<Boolean>()
    var loadingLiveData: LiveData<Boolean> = _loadingLiveData

    fun addToFav(product: Result, amount: String, context: Context) =
        viewModelScope.launch {
            val ee = SharedPreferences().getCurrentUser(context)


            val repo = repository.postuserOrders(
                userOrders = PostUserOrders(
                    email = ee.name,
                    name = ee.email,
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
                    argument = ee.id + product.objectId,))


            withContext(Dispatchers.IO) {

                if (repo.isSuccessful) {
                    product.isFavorite = true
                    dao.updateDATABASE(resultToCascheMapper.map(product))
                    repository.addtoFav(product = cascheToResultMapper.map(product))
                    _orderDeleteLiveData.value = "${product.title!!} был добавлен в избранные"
                }
            }
        }


    fun deleteOrder(id: String) = viewModelScope.launch {
        val response = repository.deleteOrderInServer(id)
        if (response.isSuccessful) {
            repository.deleteOrderInBASE(id)
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

    fun checkOrder(title: String) = viewModelScope.launch {

        val rr = dao.getUsersOrderTITLE(title)
        val dd = dao.getUsersOrder()

        dd.forEach {
            if (it.title == rr.toString()) {
                _loadingLiveData.postValue(true)
            }else if (it.title != rr.toString()){
                _loadingLiveData.postValue(false)
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