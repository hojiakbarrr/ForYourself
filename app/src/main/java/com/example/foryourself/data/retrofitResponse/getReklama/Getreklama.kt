package com.example.foryourself.data.retrofitResponse.getReklama

import com.google.gson.annotations.SerializedName

data class Getreklama(
    @SerializedName("results")
    val resultss: List<ResultofReklama>
)