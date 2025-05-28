package com.hse.courseworkcompose.data.network.apiService

import com.hse.courseworkcompose.util.ApiResponse
import com.hse.courseworkcompose.data.network.request.UserRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiServiceUser {

    @POST("/user/registration")
    suspend fun registerUser(@Body request: UserRequest): ApiResponse

    @POST("/user/login")
    suspend fun logIn(@Body request: UserRequest): ApiResponse

    @GET("/user/get/id/{id}")
    suspend fun getUserById(@Path("id") globalId:String): ApiResponse

    @GET("/user/get/loyalty/{userGlobalId}")
    suspend fun getLoyaltyCard(@Path("userGlobalId") userGlobalId:String): ApiResponse

}