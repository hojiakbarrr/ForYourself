package com.example.foryourself.data.retrofitResponse.userOrders.getUserOrders

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class ResultUsersOrder(
    val amount: String?,
    var argument: String?,
    val argument2: String?,
    val category: String?,
    val colors: String?,
    val colors1: String?,
    val colors2: String?,
    val colors3: String?,
    val createdAt: String?,
    val description: String?,
    val eighthSize: String?,
    val email: String?,
    val fifthSize: String?,
    val firstSize: String?,
    val fourthSize: String?,
    val image_first: ImageFirst?,
    val image_main: ImageMain?,
    val image_third: ImageThird?,
    val name: String?,
    val numberTelephone: String?,
    @NonNull
    @PrimaryKey
    val objectId: String,
    val price: String?,
    val season: String?,
    val secondSize: String?,
    val seventhSize: String?,
    val sixthSize: String?,
    val status: String?,
    val thirdSize: String?,
    val tipy: String?,
    val title: String?,
    val updatedAt: String?,
    val youtubeTrailer: String?
)