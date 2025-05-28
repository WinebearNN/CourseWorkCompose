package com.hse.courseworkcompose.data.network.apiService

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiServiceProvider {

    private const val BASE_URL = "http://10.0.2.2:8080"

    private const val TOKEN="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyQHhhbXBsZS5jb20iLCJpYXQiOjE3NDc5MTg1NjYsImV4cCI6OTAwMDAwMTc0NzkxODU2Nn0.KHw43W9ItjZMPk4YHZmqSg5dZQEU4F61oTmONWuM3SQ"

    private val authInterceptor = Interceptor { chain ->
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer $TOKEN")
            .build()
        chain.proceed(newRequest)
    }
    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .build()


    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiServiceUser: ApiServiceUser by lazy {
        retrofit.create(ApiServiceUser::class.java)
    }

    val apiServiceAdvertisement: ApiServiceAdvertisement by lazy {
        retrofit.create(ApiServiceAdvertisement::class.java)
    }
    val apiServiceSelection: ApiServiceSelection by lazy {
        retrofit.create(ApiServiceSelection::class.java)
    }

    val apiServiceOrder: ApiServiceOrder by lazy {
        retrofit.create(ApiServiceOrder::class.java)
    }

}