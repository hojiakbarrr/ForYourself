package com.example.foryourself.data.retrofitResponse.reklama.getReklama

import com.google.gson.annotations.SerializedName

data class Getreklama(
    @SerializedName("results")
    val resultss: List<ResultofReklama>
)