package com.example.kapriz.di

import android.content.Context
import com.example.foryourself.repository.OrderRepository
import com.example.kapriz.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    fun provideApplication(@ApplicationContext app: Context): Context = app


}