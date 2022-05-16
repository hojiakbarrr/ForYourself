package com.example.foryourself.di

import android.content.Context
import com.example.foryourself.utils.ResourceProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object UtilsModule {

    @Provides
    fun provideApplication(@ApplicationContext app: Context): Context = app


    @Provides
    fun provideResourceProvider( app: Context): ResourceProvider = ResourceProvider.Base(app)





}