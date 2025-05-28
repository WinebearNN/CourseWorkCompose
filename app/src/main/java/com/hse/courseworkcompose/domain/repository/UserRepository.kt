package com.hse.courseworkcompose.domain.repository

import com.hse.courseworkcompose.domain.entity.LoyaltyCard
import com.hse.courseworkcompose.domain.entity.User

interface UserRepository {
    suspend fun register(email: String,
                         password: String,
                         name:String,
                         surname:String,
                         phoneNumber:String,
                         dob:Long
    ): Result<Unit>
    suspend fun login(password:String,email:String): Result<Unit>
    suspend fun authUser(): Result<User>
    suspend fun getUser(): Result<User>
    suspend fun logout()
    suspend fun getLoyaltyCard(userGlobalId:Long): Result<LoyaltyCard>
    suspend fun refreshUserData(): Result<User>
}