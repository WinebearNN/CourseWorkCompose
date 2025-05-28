package com.hse.courseworkcompose.data.network.apiService

import com.hse.courseworkcompose.data.network.request.SelectionRequest
import com.hse.courseworkcompose.util.ApiResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiServiceSelection {

    @POST("/selection/add/")
    suspend fun createSelection(@Body selection: SelectionRequest): ApiResponse

    @GET("/selection/get/list/{userGlobalId}")
    suspend fun getSelectionList(@Path("userGlobalId") userGlobalId:String): ApiResponse

}