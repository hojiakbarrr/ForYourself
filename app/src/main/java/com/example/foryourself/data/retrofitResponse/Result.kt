package com.example.foryourself.data.retrofitResponse

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


data class Result(

    val createdAt: String ,
    val description: String,
    val eighthSize: String,
    val fifthSize: String,
    val firstSize: String,
    val fourthSize: String,
    val image_first: ImageFirst,
    val image_main: ImageMain,
    val image_third: ImageThird,
    val objectId: String,
    val price: String,
    val secondSize: String,
    val seventhSize: String,
    val sixthSize: String,
    val thirdSize: String,
    val title: String,
    val updatedAt: String,
    val youtubeTrailer: String

)