package com.example.foryourself.data.types

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

data class TypeProduct(
    var objectId: String?,

    var title: String?,
    var prod_descrip: String?,
    var prod_price: String?,
    var prod_trailer: String?,
    var prod_size_one: String?,
    var prod_size_two: String?,
    var prod_size_three: String?,
    var prod_size_four: String?,
    var prod_size_five: String?,
    var prod_size_six: String?,
    var prod_size_seven: String?,
    var prod_size_eight: String?,

    var put_main_photo: Image?,
    var put_2_photo: Image?,
    var put_3_photo: Image?,
    val putID: String?,
    val colors: String?,
    val season: String?,
    val colors1: String?,
    val colors2: String?,
    val colors3: String?,
    val tipy: String?,
    val category: String?


    )
