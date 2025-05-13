package com.hse.courseworkcompose.data.repository

import android.util.Log
import com.google.gson.Gson
import com.hse.courseworkcompose.data.datasource.user.LocalDataSourceUser
import com.hse.courseworkcompose.data.datasource.user.RemoteDataSourceUser
import com.hse.courseworkcompose.data.network.response.UserResponse
import com.hse.courseworkcompose.domain.entity.LoyaltyCard
import com.hse.courseworkcompose.domain.entity.User
import com.hse.courseworkcompose.domain.repository.UserRepository

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val remoteDataSourceUser: RemoteDataSourceUser,
    private val localDataSourceUser: LocalDataSourceUser
) : UserRepository {

    companion object {
        private const val TAG = "UserRepositoryImpl"
    }

    override suspend fun getUser(): Result<User> {
        localDataSourceUser.getUser(1)?.let { user: User ->
            return Result.success(user)
        }
        return Result.failure(Exception("User not found"))
    }

    override suspend fun updateUserData(user: User): Result<Unit> {
        val result = remoteDataSourceUser.updateUserData(user)
        if (result.isSuccess) localDataSourceUser.updateUserData(user)
        return result
    }

    override suspend fun uploadImageToServer(globalId: String, array: ByteArray): Result<Unit> {
        return remoteDataSourceUser.uploadImageToServer(globalId, array)
    }

    override suspend fun refreshUserData(): Result<User> {
        val user = localDataSourceUser.getAllUsers()!!.last()
        val result = remoteDataSourceUser.getUserById(user.globalId.toString())
        if (result.isSuccess) {
            localDataSourceUser.updateUserData(result.getOrThrow())
            return result
        }
        else {
            return Result.success(user)
        }
    }

    override suspend fun logout() {
        localDataSourceUser.removeAll()
    }

    override suspend fun getUsers(name: String): Result<List<User>> {
        return remoteDataSourceUser.getUsersByName(name)
    }

    override suspend fun getLoyaltyCard(userGlobalId: Long): Result<LoyaltyCard> {
        return remoteDataSourceUser.getLoyaltyLevel(userGlobalId)
    }


    override suspend fun register(
        email: String,
        password: String,
        name: String,
        phoneNumber: String
    ): Result<Unit> {

        val result = remoteDataSourceUser.registerUser(
            name = name,
            email = email,
            password = password,
            phoneNumber = phoneNumber
        )

        val userResponse = Gson().fromJson<UserResponse>(
            result.getOrNull()!!,
            UserResponse::class.java
        )
        val user = User(
            globalId = userResponse.globalId,
            email = userResponse.email,
            password = userResponse.password,
            surname = userResponse.surname,
            name = userResponse.name,
            dateOfBirth = userResponse.dob,
            country = userResponse.country,
            phoneNumber = userResponse.phoneNumber
        )

        if (result.isSuccess) {
            Log.i(TAG, "GlobalId is ${user.globalId}")
            localDataSourceUser.saveUser(user)
            return Result.success(Unit)
        }
        return Result.failure(Exception(result.getOrNull()))
    }

    override suspend fun login(password: String, email: String): Result<Unit> {
        val result = remoteDataSourceUser.login(email, password)
        if (result.isSuccess) {
            val userResponse = Gson().fromJson<UserResponse>(
                result.getOrNull(),
                UserResponse::class.java
            )
            val user = User(
                globalId = userResponse.globalId,
                email = userResponse.email,
                password = userResponse.password,
                surname = userResponse.surname,
                name = userResponse.name,
                dateOfBirth = userResponse.dob,
                country = userResponse.country,
                phoneNumber = userResponse.phoneNumber
            )

            localDataSourceUser.saveUser(user)
            return Result.success(Unit)
        }
        return Result.failure(Exception(result.getOrNull()))
    }

    override suspend fun authUser(): Result<User> {
        val users = localDataSourceUser.getAllUsers()!!
        return if (users.isNotEmpty()) {
            Result.success(users.last())
        } else {
            Result.failure(Exception("User not auth before"))

        }
    }
}