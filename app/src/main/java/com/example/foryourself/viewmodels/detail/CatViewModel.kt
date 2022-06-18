package com.example.foryourself.viewmodels.detail

import android.content.Context
import androidx.lifecycle.*
import com.example.foryourself.utils.SharedPreferences
import com.example.foryourself.data.retrofitResponse.order.getOrder.Result
import com.example.foryourself.data.retrofitResponse.userOrders.postUserOrders.PostUserOrders
import com.example.foryourself.db.ProductDao
import com.example.foryourself.db.model.FavoritesCache
import com.example.foryourself.db.model.ResultCache
import com.example.foryourself.repository.OrderRepository
import com.example.foryourself.utils.Mapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class CatViewModel @Inject constructor(
    private val repository: OrderRepository,
    private val dao: ProductDao,
    private val resultToCascheMapper: Mapper<Result, ResultCache>,
    private val cascheToResultMapper: Mapper<ResultCache, Result>,
    private val resultToFavoritCascheMapper: Mapper<Result, FavoritesCache>,


    ) : ViewModel() {

    private var _orderLiveData = MutableLiveData<List<Result>>()
    private var _searchLiveData = MutableLiveData<List<Result>>()

    var searchLiveData: LiveData<List<Result>> = _searchLiveData

    private var _loadingLiveData = MutableLiveData<Boolean>()
    var loadingLiveData: LiveData<Boolean> = _loadingLiveData


    private var _errorLiveData = MutableLiveData<String>()
    var errorLiveData: LiveData<String> = _errorLiveData


    private var _favLiveData = MutableLiveData<String>()
    var favLiveData: LiveData<String> = _favLiveData


    fun allOrderss(word: String) =
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            _loadingLiveData.postValue(true)

            _loadingLiveData.postValue(false)
            val resuk = dao.getcategory(word)

            emit(resuk?.map {
                cascheToResultMapper.map(it)

            })


            _loadingLiveData.postValue(false)
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


}