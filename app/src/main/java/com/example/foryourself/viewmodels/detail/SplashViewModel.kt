package com.example.foryourself.viewmodels.detail

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.example.foryourself.SharedPreferences
import com.example.foryourself.data.retrofitResponse.order.getOrder.Result
import com.example.foryourself.data.retrofitResponse.users.getUsers.ResultUserdata
import com.example.foryourself.data.retrofitResponse.users.postUser.PutUsers
import com.example.foryourself.db.ProductDao
import com.example.foryourself.db.model.FavoritesCache
import com.example.foryourself.db.model.ResultCache
import com.example.foryourself.repository.OrderRepository
import com.example.foryourself.utils.Mapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(
    private val dao: ProductDao,
    private val repository: OrderRepository,
    private val resultToCascheMapper: Mapper<Result, ResultCache>,
    private val resultToFavoritCascheMapper: Mapper<Result, FavoritesCache>,
    private val cascheToResultMapper: Mapper<ResultCache, Result>

) : ViewModel() {
    // TODO: Implement the ViewModel

    private var _loadingtoastLiveData = MutableLiveData<String>()
    var loadingtoastLiveData: LiveData<String> = _loadingtoastLiveData

    private var _loadingLiveData = MutableLiveData<Boolean>()
    var loadingLiveData: LiveData<Boolean> = _loadingLiveData

    private var _orderLiveData = MutableLiveData<List<Result>>()
    var orderLiveData: LiveData<List<Result>> = _orderLiveData

    fun getallOrders(context: Context) =
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            _loadingLiveData.postValue(true)
            val ee = SharedPreferences().getCurrentUser(context)

            if (dao.getProductsFromDATABASE().isEmpty()) {
                val tovary = repository.getOrders()
                val luybimye = withContext(Dispatchers.IO) { repository.getuserOrders() }

                if (tovary.isSuccessful) {
                    if (luybimye.isSuccessful) {
                        tovary.body()?.results?.forEach { toooovary ->
                            luybimye.body()?.ressults?.forEach { favorit ->
                                val argument = ee.id + toooovary.objectId
                                if (favorit.argument == argument) toooovary.isFavorite = true
                                repository.addtoFav(product = resultToFavoritCascheMapper.map(toooovary))
                                dao.addProductsFromService(resultToCascheMapper.map(toooovary))
                            }
                        }
                    }
//                    val results = checkIsFavorite(tovary.body()!!.results!!, context)
//                    results.collectLatest { product ->
//
////                        product.map {
////                            dao.addProductsFromService(resultToCascheMapper.map(it))
////                        }
//                    }
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


    private fun checkIsFavorite(results: List<Result>,context: Context): Flow<List<Result>> = flow {
        val ee = SharedPreferences().getCurrentUser(context)
        val response = withContext(Dispatchers.IO) { repository.getuserOrders() }
        if (response.isSuccessful) {
            response.body()!!.ressults.forEach { userOrder ->
                results.forEach { order ->
                    val argument = ee.id + order.objectId
                    if (userOrder.argument == argument) order.isFavorite = true
                    Log.i("rewq", userOrder.argument!!)
                }
            }
            emit(results)
        }
    }


    fun getuserOrder(googleEmail: String, googlename: String, context: Context) =
        viewModelScope.launch {
            var bb: com.example.foryourself.data.retrofitResponse.users.getUsers.ResultUserdata? =
                null

            val ee = SharedPreferences().getCurrentUser(context)


            val response = repository.getUser()
            if (response.isSuccessful) {
                response.body()?.rrresults?.forEach { r ->
                    bb = r
                    if (r.email == ee.email && r.name == ee.name) {
                        dao.addUsers(r)
                    } else {
                        val rr = repository.postUser(
                            user = PutUsers(
                                email = googleEmail,
                                name = googlename
                            )
                        )
                        if (rr.isSuccessful) {
                            dao.addUsers(
                                ResultUserdata(
                                    email = googleEmail,
                                    name = googlename,
                                    objectId = rr.body()!!.objectId,
                                    createdAt = rr.body()!!.createdAt,
                                    numberTelephone = "",
                                    updatedAt = ""
                                )
                            )
                            //                    _loadingtoastLiveData.postValue("С возвращением${bb!!.name}")
                        }
                    }
                }
            }
        }


}