package com.hse.courseworkcompose.data.datasource.advertisement

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.hse.courseworkcompose.data.network.apiService.ApiServiceAdvertisement
import com.hse.courseworkcompose.data.network.response.AdvertisementResponse
import com.hse.courseworkcompose.domain.entity.Advertisement
import com.hse.courseworkcompose.domain.entity.Filter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject


private const val MAX_RETRIES = 3
private const val RETRY_DELAY_MS = 10_000L // 10 секунд

class RemoteDataSourceAdvertisement @Inject constructor(
    private val apiServiceAdvertisement: ApiServiceAdvertisement
){

    private val gson: Gson = GsonBuilder()
        .create()

    companion object {
        private const val TAG = "RemoteDataSourceAdvertisement"
    }

    suspend fun getAdvertisementShortById(globalId: String): Result<String> = withContext(Dispatchers.IO) {
        var currentRetry = 0
        var lastException: Exception? = null

        while (currentRetry < MAX_RETRIES) {
            try {
                val response = apiServiceAdvertisement.getAdvertisementShortById(globalId)
                if (response.success) {
                    return@withContext Result.success(response.message)
                } else {
                    lastException = Exception("Server error: ${response.message}")
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


    suspend fun getAdvertisementById(globalId: String): Result<String> = withContext(Dispatchers.IO) {
        var currentRetry = 0
        var lastException: Exception? = null

        while (currentRetry < MAX_RETRIES) {
            try {
                val response = apiServiceAdvertisement.getAdvertisementById(globalId)
                if (response.success) {
                    return@withContext Result.success(response.message)
                } else {
                    lastException = Exception("Server error: ${response.message}")
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

    suspend fun getAdvertisementListByFilter(filter: Filter): Result<String> = withContext(Dispatchers.IO) {
        var currentRetry = 0
        var lastException: Exception? = null

        while (currentRetry < MAX_RETRIES) {
            try {
                val response = apiServiceAdvertisement.getAdvertisementListByFilter(filter)
                if (response.success) {
                    return@withContext Result.success(response.message)
                } else {
                    lastException = Exception("Server error: ${response.message}")
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