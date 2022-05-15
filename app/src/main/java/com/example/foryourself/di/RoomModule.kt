package com.example.foryourself.di

import android.content.Context
import androidx.room.Room
import com.example.foryourself.db.ProductDao
import com.example.foryourself.db.ProductDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RoomModule {


    @Provides
    @Singleton
    fun provideBookDB(
        @ApplicationContext app: Context,
    ): ProductDataBase = Room.databaseBuilder(
        app,
        ProductDataBase::class.java,
        "products_table"
    ).build()

    @Provides
    @Singleton
    fun provideProductDao(db: ProductDataBase): ProductDao =
        db.get()
}