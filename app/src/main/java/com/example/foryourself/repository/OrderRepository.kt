package com.example.foryourself.repository

import android.util.Log
import com.example.foryourself.data.retrofitResponse.updateResponse.UpdateResponse
import com.example.foryourself.data.retrofitResponse.deleteResponse.DeleteResponse
import com.example.foryourself.data.retrofitResponse.getResponse.Result
import com.example.foryourself.data.retrofitResponse.getResponse.TestResponse
import com.example.foryourself.data.retrofitResponse.postResponse.PostResponseAnswer
import com.example.foryourself.data.retrofitResponse.postResponse.Result_2
import com.example.foryourself.db.ProductDao
import com.example.foryourself.db.model.ResultCache
import com.example.foryourself.utils.Mapper
import com.example.foryourself.utils.Resource
import com.example.foryourself.utils.ResourceProvider
import com.example.kapriz.api.ApiService
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class OrderRepository @Inject constructor(
    private val apiService: ApiService,
    private val dao: ProductDao,
    private val resourceProvider: ResourceProvider,
    private val resultToCascheMapper: Mapper<Result, ResultCache>,
    private val cascheToResultMapper: Mapper<ResultCache, Result>
) {

    suspend fun getOrders(): Response<TestResponse> = apiService.getOrders()

    suspend fun postOrders(result: Result_2): Response<PostResponseAnswer> =
        apiService.createPost(post = result)

    fun fetchOneOrder(id: String) = flow {
        val result = dao.getOneProductDetail(id)
        if (result != null) {
            try {
                emit(Resource.success(cascheToResultMapper.map(result)))
            } catch (e: Exception) {
            }
        }
    }

    suspend fun deleteOrderInServer(id: String): Response<DeleteResponse> =
        apiService.deleteOrder(objectId = id)

    suspend fun deleteOrderInBASE(id: String) {
        dao.deleteDATABASE(id)
    }

    suspend fun updateOrder(id: String,result: Result_2) : Response<UpdateResponse> = apiService.upDateOrder(objectId = id, post = result)

    suspend fun updateinBASE(product: ResultCache) = dao.updateDATABASE(product = product)

    //startRegion
//    fun fetchOrders() = flow {
//        try {
//
//            emit(Resource.loading())
//            val resultList = dao.getProductsFromDATABASE().map {
//                cascheToResultMapper.map(it)
//            }
//
////            if (resultList.isEmpty()) {
//            val response = apiService.getOrders()
//            if (response.isSuccessful) {
//                val resultDATAList = response.body()?.results
////                    resultDATAList?.forEach { product ->
////                        dao.addProductsFromRepository(resultToCacheMapper.map(product))
////                    }
//                if (resultDATAList!!.isEmpty()) emit(Resource.empty())
//                else emit(Resource.success(data = resultDATAList))
//
//            } else emit(Resource.error(response.message()))
//
////            } else {
//
//
//            Log.i("resultList", dao.getProductsFromDATABASE().size.toString())
//
//
//            if (resultList.isEmpty()) emit(Resource.empty())
//            else emit(Resource.success(data = resultList))
//
////            }
//        } catch (e: Exception) {
//            emit(Resource.error(resourceProvider.errorType(e)))
//        }
//
//    }
    //endRegion


}