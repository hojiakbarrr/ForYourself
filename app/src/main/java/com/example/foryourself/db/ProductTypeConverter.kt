package com.example.foryourself.db

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.foryourself.db.model.ImageFirstCache
import com.example.foryourself.db.model.ImageMainCache
import com.example.foryourself.db.model.ImageThirdCache
import com.example.foryourself.db.model.ResultCache
import org.json.JSONObject


@TypeConverters
class ProductTypeConverter {


    @TypeConverter
    fun fromImageMain(imageMain: ImageMainCache): String {
        return JSONObject().apply {
            put("name", imageMain.name)
            put("url", imageMain.url)
            put("__type", imageMain.__type)
        }.toString()
    }

    @TypeConverter
    fun toImageMain(source: String): ImageMainCache {
        val json = JSONObject(source)
        return ImageMainCache(
            name = json.getString("name"),
            url = json.getString("url"),
            __type = json.getString("__type"),
        )
    }

    @TypeConverter
    fun fromImageFirstCache(imageFirst: ImageFirstCache): String {
        return JSONObject().apply {
            put("name", imageFirst.name)
            put("url", imageFirst.url)
            put("__type", imageFirst.__type)
        }.toString()
    }

    @TypeConverter
    fun toImageFirstCache(source: String): ImageFirstCache {
        val json = JSONObject(source)
        return ImageFirstCache(
            name = json.getString("name"),
            url = json.getString("url"),
            __type = json.getString("__type"),
        )
    }


    @TypeConverter
    fun fromImageThirdCache(imageThird: ImageThirdCache): String {
        return JSONObject().apply {
            put("name", imageThird.name)
            put("url", imageThird.url)
            put("__type", imageThird.__type)
        }.toString()
    }

    @TypeConverter
    fun toImageThirdCache(source: String): ImageThirdCache {
        val json = JSONObject(source)
        return ImageThirdCache(
            name = json.getString("name"),
            url = json.getString("url"),
            __type = json.getString("__type"),
        )
    }

}