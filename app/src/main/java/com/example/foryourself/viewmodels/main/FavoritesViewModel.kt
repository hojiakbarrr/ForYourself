package com.example.foryourself.viewmodels.main

import android.content.Context
import androidx.lifecycle.*
import com.example.foryourself.utils.SharedPreferences
import com.example.foryourself.data.retrofitResponse.order.getOrder.Result
import com.example.foryourself.data.retrofitResponse.userOrders.postUserOrders.PostAdmin
import com.example.foryourself.data.retrofitResponse.userOrders.postUserOrders.PostUserOrders
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
class FavoritesViewModel @Inject constructor(
    private val dao: ProductDao,
    private val resultToCascheMapper: Mapper<Result, ResultCache>,
    private val resultToFavoritCascheMapper: Mapper<Result, FavoritesCache>,
    private val mapper: Mapper<FavoritesCache, ResultCache >,


    private val repository: OrderRepository,

    ) : ViewModel() {
    private var _orderLiveData = MutableLiveData<List<FavoritesCache>>()
    var orderLiveData: LiveData<List<FavoritesCache>> = _orderLiveData


    fun deleteOrder(product: FavoritesCache) = viewModelScope.launch {
        val rr = repository.deleteuserOrders(product.objectId)
        if (rr.isSuccessful) {
            product.isFavorite = false
            dao.updateDATABASE(mapper.map(product))
            dao.deleteFromFav(product)
        }
    }

    fun postAdminRow(name: String?, numberTelephone: String?) = viewModelScope.launch {
        val ff = dao.getFavorites()
        ff.forEach { favorit ->
            val rr = repository.postAdminUserOrder(
                PostAdmin(
                    foradmin = "admin",
                    telephone = numberTelephone,
                    title = favorit.title,
                    userorderid = favorit.objectId,
                    userordername = name
                )
            )
            if (rr.isSuccessful) {

            }
        }
    }

    fun favoritesUserOrders(context: Context) =
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {

            val ee = SharedPreferences().getCurrentUser(context)

            val tovary = repository.getOrders()
            val luybimye = withContext(Dispatchers.IO) { repository.getuserOrders() }

            if (dao.getFavorites().isNullOrEmpty()) {
                if (luybimye.isSuccessful) {
                    tovary.body()?.results?.forEach { toooovary ->
                        luybimye.body()?.ressults?.forEach { favorit ->
                            val argument = ee.id + toooovary.objectId
                            if (favorit.argument == argument) {

                                toooovary.isFavorite = true
                                repository.addtoFav(
                                    product = resultToFavoritCascheMapper.map(
                                        toooovary
                                    )
                                )
                                postAdminRow(favorit.name, favorit.numberTelephone)
                            }
                        }
                    }
                }
                emit(dao.getFavorites())
            } else {
                emit(dao.getFavorites())
            }

        }
}


