package com.example.foryourself.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.foryourself.data.retrofitResponse.Result
import com.example.foryourself.data.types.TypeProduct
import com.example.foryourself.db.model.ResultCache


@Database(entities = [ResultCache :: class], version = 1)
@TypeConverters(ProductTypeConverter::class)
abstract class ProductDataBase: RoomDatabase() {

    abstract fun get(): ProductDao

}