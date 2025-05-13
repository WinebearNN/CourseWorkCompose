package com.hse.courseworkcompose.data.network.apiService

import com.hse.courseworkcompose.util.ApiResponse
import com.hse.courseworkcompose.data.network.request.UserRequest
import com.hse.courseworkcompose.domain.entity.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiServiceUser {

    @POST("/user/registration/")
    suspend fun registerUser(@Body request: UserRequest): ApiResponse


    @POST("/user/get/email/{email}")
    suspend fun logIn(@Body request: UserRequest): ApiResponse


    @POST("/user/update/")
    suspend fun updateUserData(@Body user: User): ApiResponse

    @POST("/user/avatar/upload/{id}")
    suspend fun uploadAvatarToServer(@Path("id") globalId:String, @Body array: ByteArray): ApiResponse

    @GET("/user/get/id/{id}")
    suspend fun getUserById(@Path("id") globalId:String): ApiResponse

    @GET("/user/get/name/{name}")
    suspend fun getUsersByName(@Path("name") name:String): ApiResponse

    @GET("/user/get/loyalty/{userGlobalId}")
    suspend fun getLoyaltyCard(@Path("userGlobalId") userGlobalId:String): ApiResponse

}