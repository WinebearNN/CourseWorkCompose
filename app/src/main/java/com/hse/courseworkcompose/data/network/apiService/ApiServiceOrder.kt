package com.hse.courseworkcompose.data.network.apiService

import com.hse.courseworkcompose.data.network.request.OrderRequest
import com.hse.courseworkcompose.util.ApiResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiServiceOrder {

    @POST("/order/placing")
    suspend fun placingOrder(@Body order: OrderRequest): ApiResponse

    @GET("/order/list/get/{userGlobalId}")
    suspend fun getAllOrdersByUserId(@Path("userGlobalId") userGlobalId:String): ApiResponse

}