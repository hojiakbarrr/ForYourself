package com.example.foryourself.db

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.foryourself.db.model.*
import org.json.JSONObject


@TypeConverters
class FavoriteTypeConverter {


    @TypeConverter
    fun fromImageMain(imageMain: ImageMainCacheFAV): String {
        return JSONObject().apply {
            put("name", imageMain.name)
            put("url", imageMain.url)
            put("__type", imageMain.__type)
        }.toString()
    }

    @TypeConverter
    fun toImageMain(source: String): ImageMainCacheFAV {
        val json = JSONObject(source)
        return ImageMainCacheFAV(
            name = json.getString("name"),
            url = json.getString("url"),
            __type = json.getString("__type"),
        )
    }

    @TypeConverter
    fun fromImageFirstCache(imageFirst: ImageFirstCacheFAV): String {
        return JSONObject().apply {
            put("name", imageFirst.name)
            put("url", imageFirst.url)
            put("__type", imageFirst.__type)
        }.toString()
    }

    @TypeConverter
    fun toImageFirstCache(source: String): ImageFirstCacheFAV {
        val json = JSONObject(source)
        return ImageFirstCacheFAV(
            name = json.getString("name"),
            url = json.getString("url"),
            __type = json.getString("__type"),
        )
    }


    @TypeConverter
    fun fromImageThirdCache(imageThird: ImageThirdCacheFAV): String {
        return JSONObject().apply {
            put("name", imageThird.name)
            put("url", imageThird.url)
            put("__type", imageThird.__type)
        }.toString()
    }

    @TypeConverter
    fun toImageThirdCache(source: String): ImageThirdCacheFAV {
        val json = JSONObject(source)
        return ImageThirdCacheFAV(
            name = json.getString("name"),
            url = json.getString("url"),
            __type = json.getString("__type"),
        )
    }

}