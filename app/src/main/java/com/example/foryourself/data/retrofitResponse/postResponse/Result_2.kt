package com.example.foryourself.data.retrofitResponse.postResponse

import com.example.foryourself.data.retrofitResponse.getResponse.ImageFirst
import com.example.foryourself.data.retrofitResponse.getResponse.ImageMain
import com.example.foryourself.data.retrofitResponse.getResponse.ImageThird

data class Result_2(
    val description: String,
    val eighthSize: String,
    val fifthSize: String,
    val firstSize: String,
    val fourthSize: String,
    val image_first: ImageFirst,
    val image_main: ImageMain,
    val image_third: ImageThird,
    val price: String,
    val secondSize: String,
    val seventhSize: String,
    val sixthSize: String,
    val thirdSize: String,
    val title: String,
    val youtubeTrailer: String
)