package com.example.foryourself.data.retrofitResponse.getReklama

import com.google.gson.annotations.SerializedName



data class ResultofReklama(
    val createdAt: String,
    val objectId: String,
    val reklama1: Reklama1,
    val reklama2: Reklama2,
    val reklama3: Reklama3,
    val updatedAt: String
)