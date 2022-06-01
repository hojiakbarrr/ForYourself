package com.example.foryourself.db

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.foryourself.data.retrofitResponse.getReklama.Reklama1
import com.example.foryourself.data.retrofitResponse.getReklama.Reklama2
import com.example.foryourself.data.retrofitResponse.getReklama.Reklama3
import org.json.JSONObject


@TypeConverters
class ReklamaTypeConverter {


    @TypeConverter
    fun fromReklama1(imageMain: Reklama1): String {
        return JSONObject().apply {
            put("name", imageMain.name)
            put("url", imageMain.url)
            put("__type", imageMain.__type)
        }.toString()
    }

    @TypeConverter
    fun toReklama1(source: String): Reklama1 {
        val json = JSONObject(source)
        return Reklama1(
            name = json.getString("name"),
            url = json.getString("url"),
            __type = json.getString("__type"),
        )
    }

    @TypeConverter
    fun fromReklama2(imageFirst: Reklama2): String {
        return JSONObject().apply {
            put("name", imageFirst.name)
            put("url", imageFirst.url)
            put("__type", imageFirst.__type)
        }.toString()
    }

    @TypeConverter
    fun toReklama2(source: String): Reklama2 {
        val json = JSONObject(source)
        return Reklama2(
            name = json.getString("name"),
            url = json.getString("url"),
            __type = json.getString("__type"),
        )
    }


    @TypeConverter
    fun fromImageThirdCache(imageThird: Reklama3): String {
        return JSONObject().apply {
            put("name", imageThird.name)
            put("url", imageThird.url)
            put("__type", imageThird.__type)
        }.toString()
    }

    @TypeConverter
    fun toImageThirdCache(source: String): Reklama3 {
        val json = JSONObject(source)
        return Reklama3(
            name = json.getString("name"),
            url = json.getString("url"),
            __type = json.getString("__type"),
        )
    }
}