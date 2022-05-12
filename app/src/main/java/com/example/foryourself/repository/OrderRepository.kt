package com.example.foryourself.repository

import com.example.foryourself.data.retrofitResponse.*
import com.example.kapriz.api.ApiService
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

class OrderRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getOrders(): Response<TestResponse> = apiService.getOrders()

    suspend fun postOrders(result:Result_2):
            Response<PostResponseAnswer> =
        apiService.createPost(post = result)
}