package com.example.foryourself.data.retrofitResponse.users.getUsers

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ResultUserdata(
    val createdAt: String?,
    val email: String?,
    val name: String?,
    val numberTelephone: String?,
    @PrimaryKey
    @NonNull
    val objectId: String,
    val updatedAt: String?,
    val idgoogle: String?
)