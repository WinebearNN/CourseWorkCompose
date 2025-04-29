package com.hse.courseworkcompose.domain.useCase

import com.hse.courseworkcompose.domain.entity.User
import com.hse.courseworkcompose.domain.repository.UserRepository
import javax.inject.Inject

class SearchUseCase @Inject constructor(private val userRepository: UserRepository) {

    companion object {
        private const val TAG = "SearchUseCase"
    }

    suspend fun getUsersByName(name: String): Result<List<User>> {
        return userRepository.getUsers(name)
    }

//    suspend fun getUserByEmail(email:String):Result<User> {
//        return userRepository.getUserByEmail(email)
//    }

}