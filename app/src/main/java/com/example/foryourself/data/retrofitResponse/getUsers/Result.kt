package com.example.foryourself.data.retrofitResponse.getUsers

data class Result(
    val createdAt: String,
    val email: String,
    val name: String,
    val numberTelephone: String,
    val objectId: String,
    val updatedAt: String
)