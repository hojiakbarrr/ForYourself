package com.example.foryourself.data.retrofitResponse.users.getUsers

import com.google.gson.annotations.SerializedName

data class GetUsers(
    @SerializedName("results")
    val rrresults: List<ResultUserdata>
)