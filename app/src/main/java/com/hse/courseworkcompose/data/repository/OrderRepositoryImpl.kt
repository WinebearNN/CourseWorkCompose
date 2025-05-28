package com.hse.courseworkcompose.data.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hse.courseworkcompose.data.datasource.order.RemoteDataSourceOrder
import com.hse.courseworkcompose.data.network.response.AdvertisementResponse
import com.hse.courseworkcompose.domain.entity.Order
import com.hse.courseworkcompose.domain.repository.OrderRepository
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val remoteDataSourceOrder: RemoteDataSourceOrder,
) : OrderRepository {

    override suspend fun getAllOrdersByUserId(userGlobalId:String): Result<List<Order>>{
        val result = remoteDataSourceOrder.getAllOrdersByUserId(userGlobalId)
        if(result.isSuccess) {
            val orderList = Gson().fromJson<List<Order>>(
                result.getOrNull(),
                object : TypeToken<List<Order>>() {}.type
            )
            return Result.success(orderList)
        }else{
            return Result.failure(Exception(result.getOrNull()))
        }

    }



    override suspend fun placingOrder(
        order: Order
    ): Result<String> {

        return remoteDataSourceOrder.placingOrder(
            order
        )

    }


}