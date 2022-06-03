package com.example.kapriz.api

import com.example.foryourself.data.retrofitResponse.users.postUser.PutUsers
import com.example.foryourself.data.retrofitResponse.updateResponse.UpdateResponse
import com.example.foryourself.data.retrofitResponse.deleteObject.DeleteObject
import com.example.foryourself.data.retrofitResponse.reklama.getReklama.Getreklama
import com.example.foryourself.data.retrofitResponse.order.getOrder.TestResponse
import com.example.foryourself.data.retrofitResponse.userOrders.getUserOrders.GetUserOrders
import com.example.foryourself.data.retrofitResponse.users.getUsers.GetUsers
import com.example.foryourself.data.retrofitResponse.order.postOrder.PostResponseAnswer
import com.example.foryourself.data.retrofitResponse.order.postOrder.Result_2
import com.example.foryourself.data.retrofitResponse.userOrders.postUserOrders.PostUserOrders
import com.example.foryourself.data.retrofitResponse.reklama.updateReklama.UpdateReklama
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("/classes/Orders")
    suspend fun getOrders(): Response <TestResponse>

    @POST("classes/Orders")
    suspend fun createPost(@Body post: Result_2): Response<PostResponseAnswer>

    @DELETE("/classes/Orders/{objectId}")
    suspend fun deleteOrder(
        @Path("objectId") objectId: String,
    ):Response<DeleteObject>

    @PUT("/classes/Orders/{objectId}")
    suspend fun upDateOrder(
        @Path("objectId") objectId: String,
        @Body post: Result_2
    ):Response<UpdateResponse>

/////////////////////////////////////////////////////////////////////////

    @GET("/classes/Reklama")
    suspend fun getReklama(): Response <Getreklama>

//  zghtzuTnVX

    @PUT("/classes/Reklama/{objectId}")
    suspend fun updateReklama(
        @Path("objectId") objectId: String,
        @Body post: UpdateReklama
    ):Response<UpdateResponse>

//////////////////////////////////////////////////////////////////////////////

    @GET("/classes/Users")
    suspend fun getUsers(): Response <GetUsers>

    @POST("classes/Users")
    suspend fun createNewUser(@Body user: PutUsers): Response<PostResponseAnswer>

    @PUT("/classes/Users/{objectId}")
    suspend fun updateUser(
        @Path("objectId") objectId: String,
        @Body user: PutUsers
    ):Response<UpdateResponse>

/////////////////////////////////////////////////////////////////////////////////////

    @GET("/classes/UserOrders")
    suspend fun getUserOrders(): Response <GetUserOrders>

    @POST("classes/UserOrders")
    suspend fun createNewUserOrders(@Body userOrders: PostUserOrders): Response<PostResponseAnswer>

    @DELETE("/classes/UserOrders/{objectId}")
    suspend fun deleteUserOrders(
        @Path("objectId") objectId: String,
    ):Response<DeleteObject>

    @PUT("/classes/UserOrders/{objectId}")
    suspend fun upDateUserOrders(
        @Path("objectId") objectId: String,
        @Body userOrders: PostUserOrders
    ):Response<UpdateResponse>

}