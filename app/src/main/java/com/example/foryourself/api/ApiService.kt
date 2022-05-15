package com.example.kapriz.api

import com.example.foryourself.data.DeleteResponse
import com.example.foryourself.data.retrofitResponse.postResponse.PostResponseAnswer
import com.example.foryourself.data.retrofitResponse.postResponse.Result_2
import com.example.foryourself.data.retrofitResponse.getResponse.TestResponse
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
    ):Response<DeleteResponse>

}