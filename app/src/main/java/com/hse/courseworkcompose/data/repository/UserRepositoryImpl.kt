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





    override suspend fun refreshUserData(): Result<User> {
        val user = localDataSourceUser.getAllUsers()!!.last()
        Log.d(TAG,"Trying to get user from server by globalId ${user.globalId}")
        val result = remoteDataSourceUser.getUserById(user.globalId.toString())
        Log.d(TAG,result.toString())
        if (result.isSuccess) {
            if (result.getOrNull()!=null) {
                localDataSourceUser.updateUserData(result.getOrThrow())
                return Result.success(result.getOrThrow())
            }else{
                return Result.success(user)
            }
        }
        else {
            return result
        }
    }

    override suspend fun logout() {
        localDataSourceUser.removeAll()
    }



    override suspend fun getLoyaltyCard(userGlobalId: Long): Result<LoyaltyCard> {
        return remoteDataSourceUser.getLoyaltyLevel(userGlobalId)
    }


    override suspend fun register(
        email: String,
        password: String,
        name:String,
        surname:String,
        phoneNumber:String,
        dob:Long
    ): Result<Unit> {

        val result = remoteDataSourceUser.registerUser(
            email = email,
            password = password,
            name=name,
            surname=surname,
            phoneNumber=phoneNumber,
            dob=dob,
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
        val result = remoteDataSourceUser.login(email = email, password = password)
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