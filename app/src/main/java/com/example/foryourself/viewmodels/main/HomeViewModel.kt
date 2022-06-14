package com.example.foryourself.viewmodels.main

import android.util.Log
import androidx.lifecycle.*
import com.example.foryourself.data.retrofitResponse.order.getOrder.Result
import com.example.foryourself.data.retrofitResponse.userOrders.getUserOrders.ResultUsersOrder
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
import kotlinx.coroutines.flow.collectLatest
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


    fun addToFav(product: Result, googleEmail: String, googlename: String) =
        liveData(context = viewModelScope.coroutineContext) {
            val repo = repository.postuserOrders(
                userOrders = PostUserOrders(
                    email = googleEmail,
                    name = googlename,
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
                    argument = product.objectId!!,

                )
            )

            if (repo.isSuccessful) {
                _favLiveData.value = "${product.title!!} добавлен в избранные"
                product.isFavorite = true
                dao.updateDATABASE(resultToCascheMapper.map(product))
                emit(product)

            }

//* sen рум или аргументы дорабоать


        }


    fun addToFav22(product: Result, googleEmail: String, googlename: String)  =
        liveData(context = viewModelScope.coroutineContext) {
            val repo = repository.postuserOrders(
                userOrders = PostUserOrders(
                    email = googleEmail,
                    name = googlename,
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
                    argument = product.objectId!!,

                    )
            )

            if (repo.isSuccessful) {
                _favLiveData.value = "${product.title!!} добавлен в избранные"
                product.isFavorite = true
                dao.updateDATABASE(resultToCascheMapper.map(product))
                emit(product)

            }

        }

    fun getallOrders() = liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
        _loadingLiveData.postValue(true)

        if (dao.getProductsFromDATABASE().isEmpty()) {
            val response = repository.getOrders()
            if (response.isSuccessful) {
                val results = checkIsFavorite(response.body()!!.results!!)
                results.collectLatest { product ->
                    product.map { dao.addProductsFromService(resultToCascheMapper.map(it)) }
                }
//                response.body()!!.results!!.forEach { product ->
//                    dao.addProductsFromService(resultToCascheMapper.map(product))
//                }
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

    private fun checkIsFavorite(results: List<Result>): Flow<List<Result>> = flow {
        val response = withContext(Dispatchers.IO) { repository.getuserOrders() }
        if (response.isSuccessful) {
            response.body()!!.ressults.forEach { userOrder ->
                results.forEach { order ->
                    if (userOrder.argument == order.objectId) order.isFavorite = true
                }
            }
            emit(results)
        }
    }


    fun allOrdersREFRESH() =
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            _loadingLiveData.postValue(true)
            dao.clearTable()
            dao.clearTableUserOrder()
            delay(4000)
            val response = repository.getOrders()
            if (response.isSuccessful) {
                response.body()!!.results?.forEach { product ->
                    dao.addProductsFromService(resultToCascheMapper.map(product))
                }
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

    fun getuserOrder(googleEmail: String, googlename: String) = viewModelScope.launch {
        var bb: com.example.foryourself.data.retrofitResponse.users.getUsers.ResultUserdata? =
            null

        val response = repository.getUser()
        if (response.isSuccessful) {
            response.body()?.rrresults?.forEach { r ->
                bb = r
            }
        }

        if (bb!!.email != googleEmail && bb!!.name != googlename) {
            val repo = repository.postUser(
                user = PutUsers(
                    email = googleEmail,
                    name = googlename
                )
            )

            if (repo.isSuccessful) {

                dao.addUsers(
                    ResultUserdata(
                        email = googleEmail,
                        name = googlename,
                        objectId = repo.body()!!.objectId,
                        createdAt = repo.body()!!.createdAt,
                        numberTelephone = "",
                        updatedAt = ""
                    )
                )
            }
            _loadingtoastLiveData.postValue("Добро пожаловать${bb!!.name}")
        } else {
            response.body()!!.rrresults.forEach { r ->
                if (r.email == googleEmail && r.name == googlename) {
                    dao.addUsers(bb!!)
                    _loadingtoastLiveData.postValue("С возвращением${bb!!.name}")

                }
            }
        }
    }

    val message: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

}