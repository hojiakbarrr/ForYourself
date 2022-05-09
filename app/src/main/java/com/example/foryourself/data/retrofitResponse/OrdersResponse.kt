package com.example.foryourself.data.retrofitResponse

import com.example.foryourself.data.types.TypeProduct
import com.google.gson.annotations.SerializedName

data class OrdersResponse(
    var results: List<OrdersResponse2> ? = null
)

