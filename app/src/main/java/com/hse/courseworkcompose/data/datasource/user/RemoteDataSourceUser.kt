package com.hse.courseworkcompose.data.datasource.user

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.hse.courseworkcompose.data.network.apiService.ApiServiceUser
import com.hse.courseworkcompose.data.network.request.UserRequest
import com.hse.courseworkcompose.domain.entity.LoyaltyCard
import com.hse.courseworkcompose.domain.entity.User
import com.hse.courseworkcompose.presentation.viewmodel.profile.ProfileViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject


private const val MAX_RETRIES = 3
private const val RETRY_DELAY_MS = 10_000L

class RemoteDataSourceUser @Inject constructor(
    private val apiServiceUser: ApiServiceUser
) {
    private val gson: Gson = GsonBuilder()
        .create()

    companion object {
        private const val TAG = "RemoteDataSourceUser"
    }


    suspend fun registerUser(
        email: String,
        password: String,
        name:String,
        surname:String,
        phoneNumber:String,
        dob:Long
    ): Result<String> = withContext(Dispatchers.IO) {
        var currentRetry = 0
        var lastException: Exception? = null


        while (currentRetry < MAX_RETRIES) {
            try {
                val request = UserRequest(
                    email = email,
                    password = password,
                    name = name,
                    phoneNumber = phoneNumber,
                    dob = dob,
                    surname = surname
                )
                Log.i(TAG, "User for request $request")
                val response = apiServiceUser.registerUser(request)
                if (response.success) {
                    return@withContext Result.success(response.message)
                } else {
                    lastException = Exception("Registration failed: ${response.message}")
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



    suspend fun login(email: String, password: String): Result<String> =
        withContext(Dispatchers.IO) {
            Log.d(TAG,"Start to login: $email $password")
            var currentRetry = 0
            var lastException: Exception? = null

            while (currentRetry < MAX_RETRIES) {
                try {
                    val request = UserRequest(
                        email = email,
                        password = password,
                    )

                    val response = apiServiceUser.logIn(request)

                    Log.d(TAG,"Response login: $response")

                    if (response.success) {
                        Log.d(TAG,"Login success: ${response.message}")
                        return@withContext Result.success(response.message)
                    } else {
                        lastException = Exception("Authorization failed: ${response.message}")
                    }
                } catch (e: Exception) {
                    lastException = e
                    Log.w(TAG, "Login attempt ${currentRetry + 1} failed", e)
                }

                if (currentRetry < MAX_RETRIES - 1) {
                    delay(RETRY_DELAY_MS)
                }
                currentRetry++
            }

            Result.failure(lastException ?: Exception("Unknown error"))
        }



    suspend fun getLoyaltyLevel(userGlobalId: Long): Result<LoyaltyCard> =
        withContext(Dispatchers.IO) {
            runCatching {
                Log.d(TAG,"get loyalty card $userGlobalId")
                val response = apiServiceUser.getLoyaltyCard(userGlobalId.toString())
                Log.d(TAG,"get loyalty card response: $response")

                if (response.success) {
                    gson.fromJson(response.message, LoyaltyCard::class.java)
                } else {
                    throw Exception("Failed to upload user's avatar: ${response.message}")
                }
            }.onSuccess {
                Log.d(TAG, "Loyalty card level was received successfully")
            }.onFailure { e ->
                Log.e(TAG, "An error occurred while getting the loyalty card level", e)
            }
        }

    suspend fun getUserById(globalId: String): Result<User> = withContext(Dispatchers.IO) {
        var currentRetry = 0
        var lastException: Exception? = null

        while (currentRetry < MAX_RETRIES) {
            try {
                val response = apiServiceUser.getUserById(globalId)
                if (response.success) {
                    return@withContext Result.success(
                        gson.fromJson(
                            response.message,
                            User::class.java
                        )
                    )
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