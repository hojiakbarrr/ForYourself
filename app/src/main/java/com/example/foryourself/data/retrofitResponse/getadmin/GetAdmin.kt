package com.example.foryourself.data.retrofitResponse.getadmin

import com.google.gson.annotations.SerializedName

data class GetAdmin(
    @SerializedName("results")
    val rrresults: List<GetAdminRest>
)