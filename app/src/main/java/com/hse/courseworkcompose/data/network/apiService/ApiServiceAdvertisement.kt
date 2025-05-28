package com.hse.courseworkcompose.data.network.apiService

import com.hse.courseworkcompose.util.ApiResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiServiceAdvertisement {

    @GET("/advertisement/get/{userId}/{advertisementGlobalId}")
    suspend fun getAdvertisementById(
        @Path("advertisementGlobalId") advertisementGlobalId: String,
        @Path("userId") userId : String
    ): ApiResponse

    @GET("/advertisement/list/get/{userId}")
    suspend fun getAdvertisementList(@Path("userId") userId: String): ApiResponse

    @GET("/selection/get/advertisement/{selectionId}")
    suspend fun getAdvertisementListBySelectionId(@Path("selectionId") selectionId: String): ApiResponse

    @POST("/advertisement/favorite/{userId}/{giftId}")
    suspend fun saveFavouriteAdvertisement(@Path("giftId") advertisementGlobalId: Long,
                                           @Path("userId") userId : Long): ApiResponse

    @DELETE("/advertisement/favorite/{userId}/{giftId}")
    suspend fun deleteFavouriteAdvertisement(@Path("giftId") advertisementGlobalId: String,
                                             @Path("userId") userId : String): ApiResponse

    @GET("/advertisement/favorite/get/{userGlobalId}")
    suspend fun getAdvertisementFavoriteList(@Path("userGlobalId") userGlobalId: String): ApiResponse

}