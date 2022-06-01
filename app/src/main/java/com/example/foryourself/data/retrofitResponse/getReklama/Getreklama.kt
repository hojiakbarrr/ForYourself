package com.example.foryourself.data.retrofitResponse.getReklama

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

data class Getreklama(
    @SerializedName("results")
    val resultss: List<ResultofReklama>
)