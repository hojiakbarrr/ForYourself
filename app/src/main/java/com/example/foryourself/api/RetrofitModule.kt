package com.example.foryourself.api

import com.example.kapriz.api.ApiService
import com.example.foryourself.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun requestInterceptor() : Interceptor = Interceptor{chain ->

        val request = chain.request()
            .newBuilder()
            .cacheControl(CacheControl.Builder().maxAge(0, TimeUnit.SECONDS).build())
            .addHeader("X-Parse-Application-Id", Constants.APPLICATION_ID)
            .addHeader("X-Parse-Rest-API-Key", Constants.REST_API_KEY)
            .addHeader("Content-Type", Constants.CONTENT_TYPE)
            .build()

        return@Interceptor chain.proceed(request)
    }


    @Provides
    @Singleton
    fun httpLoggingInterceptor() : HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)


    @Provides
    @Singleton
    fun client (requestInterceptor: Interceptor, httpLoggingInterceptor: HttpLoggingInterceptor) =  OkHttpClient.Builder()
        .addInterceptor(requestInterceptor)
        .addInterceptor(httpLoggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30,TimeUnit.SECONDS)
        .writeTimeout(30,TimeUnit.SECONDS)
        .build()


    @Provides
    @Singleton
    fun builder(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    @Provides
    @Singleton
    fun api(retrofit: Retrofit) :ApiService = retrofit.create(ApiService::class.java)


}