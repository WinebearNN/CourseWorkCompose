package com.hse.courseworkcompose.data.datasource.selection

import android.util.Log
import com.hse.courseworkcompose.data.network.apiService.ApiServiceSelection
import com.hse.courseworkcompose.data.network.request.SelectionRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject


private const val MAX_RETRIES = 3
private const val RETRY_DELAY_MS = 10_000L

class RemoteDataSourceSelection @Inject constructor(
    private val apiServiceSelection: ApiServiceSelection
) {

    companion object {
        private const val TAG = "RemoteDataSourceSelection"
    }

    suspend fun createSelection(
        userGlobalId: Long,
        description: String,
        name: String,
    ): Result<String> = withContext(Dispatchers.IO) {
        var currentRetry = 0
        var lastException: Exception? = null

        while (currentRetry < MAX_RETRIES) {
            try {
                val request = SelectionRequest(
                    userGlobalId = userGlobalId,
                    description = description,
                    name = name
                )
                Log.i(TAG, "Selection for creation $request")
                val response = apiServiceSelection.createSelection(request)
                if (response.success) {
                    Log.d(TAG,"selection was created")
                    return@withContext Result.success(response.message)
                } else {
                    Log.e(TAG,"Problem with creating selection: ${response.message}")
                    lastException = Exception("Selection creation failed: ${response.message}")
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

    suspend fun getSelectionList(
        userGlobalId: String
    ): Result<String> = withContext(Dispatchers.IO) {
        var currentRetry = 0
        var lastException: Exception? = null

        while (currentRetry < MAX_RETRIES) {
            try {
                val response = apiServiceSelection.getSelectionList(userGlobalId)
                if (response.success) {
                    return@withContext Result.success(response.message)
                } else {
                    lastException = Exception("Failed to get selection list: ${response.message}")
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





