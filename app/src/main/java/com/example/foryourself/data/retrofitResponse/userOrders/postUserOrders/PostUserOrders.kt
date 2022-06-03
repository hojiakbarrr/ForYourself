package com.example.foryourself.data.retrofitResponse.userOrders.postUserOrders

import com.example.foryourself.data.retrofitResponse.order.getOrder.ImageFirst
import com.example.foryourself.data.retrofitResponse.order.getOrder.ImageMain
import com.example.foryourself.data.retrofitResponse.order.getOrder.ImageThird

data class PostUserOrders(
    val email: String? = null,
    val name: String? = null,
    val numberTelephone: String? = null,
    val status: String? = null,
    val amount: String? = null,
    val title: String? = null,
    val colors1: String? = null,
    val colors2: String? = null,
    val seventhSize: String? = null,
    val colors3: String? = null,
    val image_third: ImageThird? = null,
    val season: String? = null,
    val thirdSize: String? = null,
    val price: String? = null,
    val image_first: ImageFirst? = null,
    val youtubeTrailer: String? = null,
    val colors: String? = null,
    val firstSize: String? = null,
    val secondSize: String? = null,
    val sixthSize: String? = null,
    val fifthSize: String? = null,
    val eighthSize: String? = null,
    val fourthSize: String? = null,
    val description: String? = null,
    val image_main: ImageMain? = null,
    val category: String? = null,
    val tipy: String? = null,

    )