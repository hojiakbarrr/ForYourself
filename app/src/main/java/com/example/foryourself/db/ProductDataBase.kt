package com.example.foryourself.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.foryourself.data.retrofitResponse.reklama.getReklama.ResultofReklama
import com.example.foryourself.db.model.FavoritesCache
import com.example.foryourself.db.model.ResultCache


@Database(entities = [ResultCache :: class, ResultofReklama :: class,FavoritesCache :: class], version = 1)
@TypeConverters(ProductTypeConverter::class, ReklamaTypeConverter::class, FavoriteTypeConverter ::class)
abstract class ProductDataBase: RoomDatabase() {

    abstract fun get(): ProductDao

}