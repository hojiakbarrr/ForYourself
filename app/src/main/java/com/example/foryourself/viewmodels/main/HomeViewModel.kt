package com.example.foryourself.viewmodels.main

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.example.foryourself.utils.SharedPreferences
import com.example.foryourself.data.retrofitResponse.order.getOrder.Result
import com.example.foryourself.data.retrofitResponse.userOrders.postUserOrders.PostUserOrders
import com.example.foryourself.data.retrofitResponse.users.getUsers.ResultUserdata
import com.example.foryourself.data.retrofitResponse.users.postUser.PutUsers
import com.example.foryourself.db.ProductDao
import com.example.foryourself.db.model.FavoritesCache
import com.example.foryourself.db.model.ResultCache
import com.example.foryourself.repository.OrderRepository
import com.example.foryourself.utils.Mapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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

    private var _favLiveData = MutableLiveData<String>()
    var favLiveData: LiveData<String> = _favLiveData

    private var _loadingtoastLiveData = MutableLiveData<String>()
    var loadingtoastLiveData: LiveData<String> = _loadingtoastLiveData


    fun addToFav(product: Result, context: Context) =
        liveData(context = viewModelScope.coroutineContext) {

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
                    argument = ee.id + product.objectId,

                    )
            )
            withContext(Dispatchers.IO) {

                if (repo.isSuccessful) {
                    product.isFavorite = true
                    dao.updateDATABASE(resultToCascheMapper.map(product))
                    repository.addtoFav(product = resultToFavoritCascheMapper.map(product))
                    emit(product)
                    _favLiveData.postValue("${product.title!!} добавлен в избранные")
                }
            }
        }


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

    private fun checkIsFavorite(results: List<Result>, context: Context): Flow<List<Result>> =
        flow {
            val ee = SharedPreferences().getCurrentUser(context)
            val response = withContext(Dispatchers.IO) { repository.getuserOrders() }
            if (response.isSuccessful) {
                response.body()!!.ressults.forEach { userOrder ->
                    results.forEach { order ->
                        val argument = ee.id + order.objectId
                        if (userOrder.argument == argument) order.isFavorite = true
                        Log.i("rewq", userOrder.argument!!)
                        dao.addProductsFromService(resultToCascheMapper.map(order))
                    }
                }
                results.forEach {
                    dao.addProductsFromService(resultToCascheMapper.map(it))
                }
                emit(results)
            }
        }

    fun allOrdersREFRESH(googleEmail: String, googlename: String, id: String, context: Context) =
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            _loadingLiveData.postValue(true)
            dao.clearTable()
            dao.clearTableUserOrder()
            dao.clearTableUser()
            dao.clearTableFav()
            delay(4000)

            getallOrders(context)
            getReklama()
            getuserOrder(googleEmail, googlename, id, context)

            emit(false)
            _loadingLiveData.postValue(false)

        }

    fun getReklama() = liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
        _loadingLiveData.postValue(true)
        if (dao.getreklamaFromDATABASE().isEmpty()) {
            val response = repository.getReklama()
            if (response.isSuccessful) {
                response.body()!!.resultss.forEach {
                    dao.addReklama(it)
                }
                emit(dao.getreklamaFromDATABASE())
                _loadingLiveData.postValue(false)
            } else {
                Log.d("tree", response.message())
            }
        } else {
            emit(dao.getreklamaFromDATABASE())
            _loadingLiveData.postValue(false)
        }
    }


    fun getuserOrder(googleEmail: String, googlename: String, id: String, context: Context) =
        viewModelScope.launch {
            var bb: com.example.foryourself.data.retrofitResponse.users.getUsers.ResultUserdata? =
                null

            val ee = SharedPreferences().getCurrentUser(context)


            val response = repository.getUser()
            if (response.isSuccessful) {
                response.body()?.rrresults?.forEach { r ->
                    bb = r
                    if (r.idgoogle == ee.id) {
                        dao.addUsers(r)
                    } else if (r.idgoogle != ee.id) {
                        repository.postUser(user = PutUsers(email = googleEmail, name = googlename))
                        dao.addUsers(
                            ResultUserdata(
                                email = googleEmail,
                                name = googlename,
                                objectId = "",
                                createdAt = "",
                                numberTelephone = "",
                                updatedAt = "",
                                idgoogle = ee.id
                            )
                        )
                        //    _loadingtoastLiveData.postValue("С возвращением${bb!!.name}")
                    }
                }
            }
        }

    val message: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

}