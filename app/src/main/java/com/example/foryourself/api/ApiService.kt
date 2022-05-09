package com.example.kapriz.api

import com.example.foryourself.data.Result
import com.example.foryourself.data.TestResponse
import com.example.foryourself.data.retrofitResponse.OrdersResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("/classes/Orders")
    suspend fun getOrders(): Response <TestResponse>


}