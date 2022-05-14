package com.example.foryourself.di

import com.example.foryourself.data.retrofitResponse.Result
import com.example.foryourself.db.model.ResultCache
import com.example.foryourself.mappers.ResultCascheToResultMapper
import com.example.foryourself.mappers.ResultToCacheMapper
import com.example.foryourself.utils.Mapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object MapperModule {


    @Provides
    fun provideResultToCacheMapper() : Mapper<Result, ResultCache> = ResultToCacheMapper()

    @Provides
    fun provideResultCascheToResultMapper(): Mapper<ResultCache, Result> = ResultCascheToResultMapper()

}