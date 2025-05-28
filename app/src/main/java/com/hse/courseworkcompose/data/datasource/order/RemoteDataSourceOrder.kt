package com.hse.courseworkcompose.data.datasource.order

import android.util.Log
import com.hse.courseworkcompose.data.network.apiService.ApiServiceOrder
import com.hse.courseworkcompose.data.network.request.OrderRequest
import com.hse.courseworkcompose.domain.entity.Order
import com.hse.courseworkcompose.util.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import retrofit2.http.Path
import javax.inject.Inject


private const val MAX_RETRIES = 3
private const val RETRY_DELAY_MS = 10_000L

class RemoteDataSourceOrder @Inject constructor(
    private val apiServiceOrder: ApiServiceOrder
) {

    companion object{
        private const val TAG="RemoteDataSourceOrder"
    }


    suspend fun getAllOrdersByUserId(userGlobalId:String): Result<String> = withContext(Dispatchers.IO) {
        var currentRetry = 0
        var lastException: Exception? = null


        while (currentRetry < MAX_RETRIES) {
            try {
                Log.i(TAG, "UserId for get all orders $userGlobalId")
                val response = apiServiceOrder.getAllOrdersByUserId(userGlobalId)
                if (response.success) {
                    return@withContext Result.success(response.message)
                } else {
                    lastException = Exception("Getting order list failed: ${response.message}")
                }
            } catch (e: Exception) {
                lastException = e
                Log.w(TAG, "Attempt ${currentRetry + 1} failed", e)
            }

            if (currentRetry < MAX_RETRIES - 1) {
                delay(RETRY_DELAY_MS)
            }
            currentRetry++
        }

        Result.failure(lastException ?: Exception("Unknown error"))
    }

    suspend fun placingOrder(
        order:Order
    ): Result<String> = withContext(Dispatchers.IO) {
        var currentRetry = 0
        var lastException: Exception? = null


        while (currentRetry < MAX_RETRIES) {
            try {
                Log.i(TAG, "Order for placing $order")

                val response = apiServiceOrder.placingOrder(OrderRequest(
                    userId = order.userGlobalId,
                    idAdvertisement = order.idAdvertisement,
                    amount = order.amount,
                    globalId = 0,
                    address = order.address,
                    isByCash = order.isByCash
                ))
                if (response.success) {
                    return@withContext Result.success(response.message)
                } else {
                    lastException = Exception("Placing failed: ${response.message}")
                }
            } catch (e: Exception) {
                lastException = e
                Log.w(TAG, "Attempt ${currentRetry + 1} failed", e)
            }

            if (currentRetry < MAX_RETRIES - 1) {
                delay(RETRY_DELAY_MS)
            }
            currentRetry++
        }

        Result.failure(lastException ?: Exception("Unknown error"))
    }
}