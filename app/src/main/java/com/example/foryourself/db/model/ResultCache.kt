package com.example.foryourself.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "products_table")
data class ResultCache(
    @ColumnInfo(name = "createdAt")
    val createdAt: String,
    val description: String,
    val eighthSize: String,
    val fifthSize: String,
    val firstSize: String,
    val fourthSize: String,
    val image_first: ImageFirstCache,
    val image_main: ImageMainCache,
    val image_third: ImageThirdCache,
    @PrimaryKey
    val objectId: String,
    val price: String,
    val secondSize: String,
    val seventhSize: String,
    val sixthSize: String,
    val thirdSize: String,
    val title: String,
    val updatedAt: String,
    val youtubeTrailer: String

) : Serializable


data class ImageFirstCache(
    val __type: String,
    val name: String,
    val url: String ? =null
)


data class ImageMainCache(
    val __type: String,
    val name: String,
    val url: String? =null
)

data class ImageThirdCache(
    val __type: String,
    val name: String,
    val url: String? =null
)