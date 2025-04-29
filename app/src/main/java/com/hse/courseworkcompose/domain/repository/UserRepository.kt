package com.hse.courseworkcompose.domain.repository

import com.hse.courseworkcompose.domain.entity.User

interface UserRepository {
    suspend fun register(email: String,password: String,name:String,phoneNumber:String): Result<Unit>
    suspend fun login(password:String,email:String): Result<Unit>
    suspend fun authUser(): Result<User>
    suspend fun getUser(): Result<User>
    suspend fun updateUserData(user: User): Result<Unit>
    suspend fun uploadImageToServer(globalId:String,array: ByteArray):Result<Unit>
    suspend fun refreshUserData(): Result<User>
    suspend fun logout()
    suspend fun getUsers(name:String):Result<List<User>>
}