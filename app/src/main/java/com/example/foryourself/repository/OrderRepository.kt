package com.example.foryourself.repository

import com.example.foryourself.data.retrofitResponse.updateResponse.UpdateResponse
import com.example.foryourself.data.retrofitResponse.deleteObject.DeleteObject
import com.example.foryourself.data.retrofitResponse.reklama.getReklama.Getreklama
import com.example.foryourself.data.retrofitResponse.order.getOrder.Result
import com.example.foryourself.data.retrofitResponse.order.getOrder.TestResponse
import com.example.foryourself.data.retrofitResponse.users.getUsers.GetUsers
import com.example.foryourself.data.retrofitResponse.order.postOrder.PostResponseAnswer
import com.example.foryourself.data.retrofitResponse.order.postOrder.Result_2
import com.example.foryourself.data.retrofitResponse.users.postUser.PutUsers
import com.example.foryourself.data.retrofitResponse.reklama.updateReklama.UpdateReklama
import com.example.foryourself.data.retrofitResponse.userOrders.getUserOrders.GetUserOrders
import com.example.foryourself.data.retrofitResponse.userOrders.postUserOrders.PostUserOrders
import com.example.foryourself.db.ProductDao
import com.example.foryourself.db.model.FavoritesCache
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

    suspend fun fetchOneOrder(id: String) = flow { val result = dao.getOneProductDetail(id)
        if (result != null) {
            try {
                emit(Resource.success(cascheToResultMapper.map(result)))
            } catch (e: Exception) {
            }
        }
    }

    suspend fun deleteOrderInServer(id: String): Response<DeleteObject> = apiService.deleteOrder(objectId = id)

    suspend fun deleteOrderInBASE(id: String) { dao.deleteDATABASE(id) }

    suspend fun updateOrder(id: String,result: Result_2) : Response<UpdateResponse> = apiService.upDateOrder(objectId = id, post = result)

    suspend fun getReklama() : Response<Getreklama> = apiService.getReklama()

    suspend fun updateReklama(id: String, reklama: UpdateReklama) : Response<UpdateResponse> = apiService.updateReklama(objectId = id, post = reklama)

    suspend fun postUser(user: PutUsers) : Response<PostResponseAnswer> = apiService.createNewUser(user = user)

    suspend fun getUser(): Response<GetUsers> = apiService.getUsers()

    suspend fun updateUser(id: String,user: PutUsers): Response<UpdateResponse> = apiService.updateUser(objectId = id, user = user)

    suspend fun addtoFav(product: FavoritesCache) {dao.addFavorites(product = product)}

    suspend fun getuserOrders(): Response<GetUserOrders> = apiService.getUserOrders()

    suspend fun postuserOrders(userOrders: PostUserOrders): Response<PostResponseAnswer> = apiService.createNewUserOrders(userOrders = userOrders)

    suspend fun updateuserOrders(id: String,userOrders: PostUserOrders) : Response<UpdateResponse> = apiService.upDateUserOrders(objectId = id, userOrders = userOrders)

    suspend fun deleteuserOrders(id: String): Response<DeleteObject> = apiService.deleteUserOrders(objectId = id)


}