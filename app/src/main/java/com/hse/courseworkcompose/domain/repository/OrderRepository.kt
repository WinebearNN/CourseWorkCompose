package com.hse.courseworkcompose.domain.repository

import com.hse.courseworkcompose.domain.entity.Order

interface OrderRepository {

    suspend fun getAllOrdersByUserId(userGlobalId:String): Result<List<Order>>

    suspend fun placingOrder(order:Order): Result<String>


}