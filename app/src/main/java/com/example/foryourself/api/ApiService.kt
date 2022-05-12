package com.example.kapriz.api

import com.example.foryourself.data.retrofitResponse.PostResponseAnswer
import com.example.foryourself.data.retrofitResponse.Result
import com.example.foryourself.data.retrofitResponse.Result_2
import com.example.foryourself.data.retrofitResponse.TestResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("/classes/Orders")
    suspend fun getOrders(): Response <TestResponse>

    @POST("classes/Orders")
    suspend fun createPost(@Body post: Result_2): Response<PostResponseAnswer>


}