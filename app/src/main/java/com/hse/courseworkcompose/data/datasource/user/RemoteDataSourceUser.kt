package com.hse.courseworkcompose.data.datasource.user

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.hse.courseworkcompose.util.ApiResponse
import com.hse.courseworkcompose.data.network.apiService.ApiServiceUser
import com.hse.courseworkcompose.data.network.request.UserRequest
import com.hse.courseworkcompose.domain.entity.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject


private const val MAX_RETRIES = 3
private const val RETRY_DELAY_MS = 10_000L // 10 секунд

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
        name: String,
        phoneNumber: String
    ): Result<String> = withContext(Dispatchers.IO) {
        runCatching {
            val request = UserRequest(
                email = email,
                password = password,
                name = name,
                phoneNumber = phoneNumber
            )
            Log.i(TAG, "User for request $request")
//            var response = apiServiceUser.registerUser(request)
            val response = ApiResponse(
                success = true,
                message = """{
                    "globalId": 1,
                    "email": "vvzimin@hse.edu.ru",
                    "password": "1234567",
                    "name": "Владимир",
                    "interest": "Волейбол, рыбалка, хоккей",
                    "phoneNumber":"89524705200",
                    "link": "@winebear",
                    "friends": []
                }"""
            )
            if (response.success) response.message else throw Exception("Registration failed: ${response.message}")
        }.onFailure { e ->
            Log.e(TAG, "An error occurred during registration", e)
        }
    }

    suspend fun updateUserData(user: User): Result<Unit> = withContext(Dispatchers.IO) {
        runCatching {
            val response = apiServiceUser.updateUserData(user)
            Log.d(TAG,response.message)
            if (response.success) Unit
            else throw Exception("Failed to update user: ${response.message}")
        }.onSuccess {
            Log.d(TAG, "User updated successfully")
        }.onFailure { e ->
            Log.e(TAG, "An error occurred while fetching the user", e)
        }
    }

    suspend fun login(email: String,password:String): Result<String> = withContext(Dispatchers.IO) {
        runCatching {
            val request = UserRequest(
                email = email,
                password = password,
            )
            val response = apiServiceUser.logIn(request)
            if (response.success) response.message else throw Exception("Authorization failed: ${response.message}")
        }.onFailure { e ->
            Log.e(TAG, "An error occurred during authorization", e)
        }
    }

    suspend fun uploadImageToServer(globalId: String, array: ByteArray): Result<Unit> =
        withContext(Dispatchers.IO) {
            runCatching {
                val response = apiServiceUser.uploadAvatarToServer(globalId, array)
                if (response.success) Unit
                else {
                    throw Exception("Failed to upload user's avatar: ${response.message}")
                }
            }.onSuccess {
                Log.d(TAG, "User's avatar updated successfully")
            }.onFailure { e ->
                Log.e(TAG, "An error occurred while updating the user's avatar", e)
            }
        }



    suspend fun getUserById(globalId: String): Result<User> = withContext(Dispatchers.IO) {
        var currentRetry = 0
        var lastException: Exception? = null

        while (currentRetry < MAX_RETRIES) {
            try {
                val response = apiServiceUser.getUserById(globalId)
                if (response.success) {
                    return@withContext Result.success(gson.fromJson(response.message, User::class.java))
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

    suspend fun getUsersByName(name:String): Result<List<User>> = withContext(Dispatchers.IO){
        runCatching {
            val response=apiServiceUser.getUsersByName(name)
            if(response.success){
                val typeJson = object : TypeToken<List<User>>() {}.type
                val usersResponse: List<User> = gson.fromJson(response.message, typeJson)
                usersResponse
            }else{
                throw Exception("Failed to get users by name:${response.message}")
            }
        }.onFailure { e ->
            Log.e(TAG,"An error occered while getting list of users by name",e)
        }
    }


}