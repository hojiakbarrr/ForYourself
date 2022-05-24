package com.example.foryourself.data.retrofitResponse.getResponse

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Result(

    val createdAt: String?,
    val description: String?,
    val eighthSize: String?,
    val fifthSize: String?,
    val firstSize: String?,
    val fourthSize: String?,
    val image_first: ImageFirst?,
    val image_main: ImageMain?,
    val image_third: ImageThird?,
    val objectId: String?,
    val price: String?,
    val secondSize: String?,
    val seventhSize: String?,
    val sixthSize: String?,
    val thirdSize: String?,
    val title: String?,
    val updatedAt: String?,
    val youtubeTrailer: String?,
    val putID: String?,
    val colors: String?,
    val season: String?,
    val colors1: String?,
    val colors2: String?,
    val colors3: String?,
    val tipy: String?,
    val category: String?

): Parcelable
