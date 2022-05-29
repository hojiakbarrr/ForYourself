package com.example.foryourself.repository

import com.example.foryourself.data.retrofitResponse.updateResponse.UpdateResponse
import com.example.foryourself.data.retrofitResponse.deleteResponse.DeleteResponse
import com.example.foryourself.data.retrofitResponse.getReklama.Getreklama
import com.example.foryourself.data.retrofitResponse.getResponse.Result
import com.example.foryourself.data.retrofitResponse.getResponse.TestResponse
import com.example.foryourself.data.retrofitResponse.getUsers.GetUsers
import com.example.foryourself.data.retrofitResponse.postResponse.PostResponseAnswer
import com.example.foryourself.data.retrofitResponse.postResponse.Result_2
import com.example.foryourself.data.retrofitResponse.postUser.PutUsers
import com.example.foryourself.data.retrofitResponse.updateReklama.UpdateReklama
import com.example.foryourself.db.ProductDao
import com.example.foryourself.db.model.ResultCache
import com.example.foryourself.utils.Mapper
import com.example.foryourself.utils.Resource
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

    suspend fun getReklama() : Response<Getreklama> = apiService.getReklama()

    suspend fun updateReklama(id: String, reklama: UpdateReklama) : Response<UpdateResponse> = apiService.updateReklama(objectId = id, post = reklama)

    suspend fun postUser(user: PutUsers) : Response<PostResponseAnswer> = apiService.createNewUser(user = user)

    suspend fun getUser(): Response<GetUsers> = apiService.getUsers()

    suspend fun updateUser(id: String,user: PutUsers): Response<UpdateResponse> = apiService.updateUser(objectId = id, user = user)

}