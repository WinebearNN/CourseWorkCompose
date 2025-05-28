package com.hse.courseworkcompose.data.datasource.advertisement

import android.util.Log
import com.hse.courseworkcompose.data.network.apiService.ApiServiceAdvertisement
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject


private const val MAX_RETRIES = 3
private const val RETRY_DELAY_MS = 10_000L

class RemoteDataSourceAdvertisement @Inject constructor(
    private val apiServiceAdvertisement: ApiServiceAdvertisement
) {


    companion object {
        private const val TAG = "RemoteDataSourceAdvertisement"
    }

    suspend fun getAdvertisementList(userId:String): Result<String> = withContext(Dispatchers.IO) {
        var currentRetry = 0
        var lastException: Exception? = null

        while (currentRetry < MAX_RETRIES) {
            try {
                val response = apiServiceAdvertisement.getAdvertisementList(userId)
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




    suspend fun getAdvertisementById(globalIdAdv: String,globalIdUser: String): Result<String> =
        withContext(Dispatchers.IO) {
            var currentRetry = 0
            var lastException: Exception? = null

            while (currentRetry < MAX_RETRIES) {
                try {
                    val response = apiServiceAdvertisement.getAdvertisementById(advertisementGlobalId = globalIdAdv, userId = globalIdUser)
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



    suspend fun getAdvertisementListBySelectionId(selectionId: String): Result<String> =
        withContext(Dispatchers.IO) {
            var currentRetry = 0
            var lastException: Exception? = null

            while (currentRetry < MAX_RETRIES) {
                try {
                    val response = apiServiceAdvertisement.getAdvertisementListBySelectionId(selectionId)
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

    suspend fun saveFavouriteAdvertisement(userGlobalId:String,advertisementGlobalId: String):
            Result<Unit> = withContext(Dispatchers.IO) {
        var currentRetry = 0
        var lastException: Exception? = null

        while (currentRetry < MAX_RETRIES) {
            try {
                val response = apiServiceAdvertisement.saveFavouriteAdvertisement(
                    advertisementGlobalId = advertisementGlobalId.toLong(),
                    userId = userGlobalId.toLong()
                )
                if (response.success) {
                    return@withContext Result.success(Unit)
                } else {
                    Log.d(TAG,"save fav error: ${response.message}")
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

    suspend fun getAdvertisementListFavorite(userGlobalId: String): Result<String> =
        withContext(Dispatchers.IO) {
            var currentRetry = 0
            var lastException: Exception? = null

            while (currentRetry < MAX_RETRIES) {
                try {
                    val response =
                        apiServiceAdvertisement.getAdvertisementFavoriteList(userGlobalId)
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

    suspend fun deleteFavouriteAdvertisement(userGlobalId:String,advertisementGlobalId: String):
            Result<Unit> = withContext(Dispatchers.IO) {
        var currentRetry = 0
        var lastException: Exception? = null

        while (currentRetry < MAX_RETRIES) {
            try {
                val response = apiServiceAdvertisement.deleteFavouriteAdvertisement(
                    advertisementGlobalId = advertisementGlobalId,
                    userId = userGlobalId
                )
                if (response.success) {
                    return@withContext Result.success(Unit)
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