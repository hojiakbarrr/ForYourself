package com.example.foryourself.repository

import android.util.Log
import com.example.foryourself.data.retrofitResponse.updateResponse.UpdateResponse
import com.example.foryourself.data.retrofitResponse.deleteResponse.DeleteResponse
import com.example.foryourself.data.retrofitResponse.getReklama.Getreklama
import com.example.foryourself.data.retrofitResponse.getResponse.Result
import com.example.foryourself.data.retrofitResponse.getResponse.TestResponse
import com.example.foryourself.data.retrofitResponse.postResponse.PostResponseAnswer
import com.example.foryourself.data.retrofitResponse.postResponse.Result_2
import com.example.foryourself.data.retrofitResponse.putReklama.PuttReklama
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
    private val cascheToResultMapper: Mapper<ResultCache, Result>
) {

    suspend fun getOrders(): Response<TestResponse> = apiService.getOrders()

    suspend fun postOrders(result: Result_2): Response<PostResponseAnswer> = apiService.createPost(post = result)

    fun fetchOneOrder(id: String) = flow { val result = dao.getOneProductDetail(id)
        if (result != null) {
            try {
                emit(Resource.success(cascheToResultMapper.map(result)))
            } catch (e: Exception) {
            }
        }
    }

    suspend fun deleteOrderInServer(id: String): Response<DeleteResponse> = apiService.deleteOrder(objectId = id)

    suspend fun deleteOrderInBASE(id: String) { dao.deleteDATABASE(id) }

    suspend fun updateOrder(id: String,result: Result_2) : Response<UpdateResponse> = apiService.upDateOrder(objectId = id, post = result)

    suspend fun updateinBASE(product: ResultCache) = dao.updateDATABASE(product = product)

    suspend fun getReklama() : Response<Getreklama> = apiService.getReklama()

    suspend fun updateReklama(id: String, reklama: PuttReklama) : Response<UpdateResponse> = apiService.updateReklama(objectId = id, post = reklama)

}