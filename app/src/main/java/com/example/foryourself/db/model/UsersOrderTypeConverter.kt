package com.example.foryourself.db.model

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.foryourself.data.retrofitResponse.userOrders.getUserOrders.ImageFirst
import com.example.foryourself.data.retrofitResponse.userOrders.getUserOrders.ImageMain
import com.example.foryourself.data.retrofitResponse.userOrders.getUserOrders.ImageThird
import com.example.foryourself.db.model.*
import org.json.JSONObject


@TypeConverters
class UsersOrderTypeConverter {


    @TypeConverter
    fun fromImageMain(imageMain: ImageMain): String {
        return JSONObject().apply {
            put("name", imageMain.name)
            put("url", imageMain.url)
            put("__type", imageMain.__type)
        }.toString()
    }

    @TypeConverter
    fun toImageMain(source: String): ImageMain {
        val json = JSONObject(source)
        return ImageMain(
            name = json.getString("name"),
            url = json.getString("url"),
            __type = json.getString("__type"),
        )
    }

    @TypeConverter
    fun fromImageFirstCache(imageFirst: ImageFirst): String {
        return JSONObject().apply {
            put("name", imageFirst.name)
            put("url", imageFirst.url)
            put("__type", imageFirst.__type)
        }.toString()
    }

    @TypeConverter
    fun toImageFirstCache(source: String): ImageFirst {
        val json = JSONObject(source)
        return ImageFirst(
            name = json.getString("name"),
            url = json.getString("url"),
            __type = json.getString("__type"),
        )
    }


    @TypeConverter
    fun fromImageThirdCache(imageThird: ImageThird): String {
        return JSONObject().apply {
            put("name", imageThird.name)
            put("url", imageThird.url)
            put("__type", imageThird.__type)
        }.toString()
    }

    @TypeConverter
    fun toImageThirdCache(source: String): ImageThird {
        val json = JSONObject(source)
        return ImageThird(
            name = json.getString("name"),
            url = json.getString("url"),
            __type = json.getString("__type"),
        )
    }

}