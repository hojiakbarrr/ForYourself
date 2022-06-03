package com.example.foryourself.data.retrofitResponse.reklama.getReklama

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class ResultofReklama(
    val createdAt: String,
    @PrimaryKey
    @NonNull
    val objectId: String,
    val reklama1: Reklama1?,
    val reklama2: Reklama2?,
    val reklama3: Reklama3?,
    val updatedAt: String
)