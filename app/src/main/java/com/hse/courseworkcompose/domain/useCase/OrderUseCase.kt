package com.hse.courseworkcompose.domain.useCase

import android.util.Log
import com.hse.courseworkcompose.domain.entity.Order
import com.hse.courseworkcompose.domain.repository.OrderRepository
import javax.inject.Inject

class OrderUseCase @Inject constructor(private val orderRepository: OrderRepository)  {

    companion object {
        private const val TAG = "OrderUseCase"
    }

    suspend fun placingOrder(order:Order): Result<String> {
        val result=orderRepository.placingOrder(
            order
        )
        Log.i(TAG,"$result")
        return result
    }

    suspend fun getAllOrdersByUserId(userGlobalId:String): Result<List<Order>>{
        return orderRepository.getAllOrdersByUserId(userGlobalId)
    }



}