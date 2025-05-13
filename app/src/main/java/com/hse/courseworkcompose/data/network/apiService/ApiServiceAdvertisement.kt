package com.hse.courseworkcompose.data.network.apiService

import com.hse.courseworkcompose.domain.entity.Filter
import com.hse.courseworkcompose.util.ApiResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiServiceAdvertisement {

    @GET("/advertisement/get/{advertisementGlobalId}")
    suspend fun getAdvertisementById(@Path("advertisementGlobalId") advertisementGlobalId:String): ApiResponse

    @GET("/advertisement/short/get/{advertisementGlobalId}")
    suspend fun getAdvertisementShortById(@Path("advertisementGlobalId") advertisementShortGlobalId:String): ApiResponse

    @GET("/advertisement/list/get/")
    suspend fun getAdvertisementListByFilter(@Body filter: Filter): ApiResponse

}