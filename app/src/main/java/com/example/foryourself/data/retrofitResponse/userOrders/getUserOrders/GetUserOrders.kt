package com.example.foryourself.data.retrofitResponse.userOrders.getUserOrders

import com.google.gson.annotations.SerializedName

data class GetUserOrders(
    @SerializedName("results")
    val ressults: List<ResultUsersOrder>
)