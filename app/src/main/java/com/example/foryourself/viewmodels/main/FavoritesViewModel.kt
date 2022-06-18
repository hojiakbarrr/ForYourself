package com.example.foryourself.viewmodels.main

import android.content.Context
import androidx.lifecycle.*
import com.example.foryourself.utils.SharedPreferences
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
class FavoritesViewModel @Inject constructor(
    private val dao: ProductDao,
    private val resultToCascheMapper: Mapper<Result, ResultCache>,
    private val resultToFavoritCascheMapper: Mapper<Result, FavoritesCache>,


    private val repository: OrderRepository,

    ) : ViewModel() {
    private var _orderLiveData = MutableLiveData<List<FavoritesCache>>()
    var orderLiveData: LiveData<List<FavoritesCache>> = _orderLiveData


    fun deleteOrder(product: FavoritesCache) = viewModelScope.launch {
        val rr = repository.deleteuserOrders(product.objectId)
        if (rr.isSuccessful) {
            dao.deleteFrom(product)
        }
    }

    fun favoritesUserOrders(context: Context) =
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {

            val ee = SharedPreferences().getCurrentUser(context)

            val tovary = repository.getOrders()
            val luybimye = withContext(Dispatchers.IO) { repository.getuserOrders() }

            if (dao.getFavorites().isNullOrEmpty()){
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
                            }
                        }
                    }
                }
                emit(dao.getFavorites())
            }else{
                emit(dao.getFavorites())
            }

        }
}


