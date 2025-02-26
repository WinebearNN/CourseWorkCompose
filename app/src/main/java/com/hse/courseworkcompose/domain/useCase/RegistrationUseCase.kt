package com.hse.courseworkcompose.domain.useCase

import com.hse.courseworkcompose.domain.entity.User
import com.hse.courseworkcompose.domain.repository.UserRepository
import com.hse.courseworkcompose.utills.CheckValidation.Companion.isValidEmail
import com.hse.courseworkcompose.utills.CheckValidation.Companion.isValidPassword
import com.hse.courseworkcompose.utills.ErrorCode
import com.hse.courseworkcompose.utills.ErrorException
import com.hse.courseworkcompose.utills.GlobalError
import javax.inject.Inject

class RegistrationUseCase @Inject constructor(private val userRepository: UserRepository) {

    companion object {
        private const val TAG = "RegisterUserUseCase"
    }

    suspend fun execute(user: User): Result<Unit> {
        val globalErrors = mutableListOf<GlobalError>()

        validateUser(user, globalErrors)

        if (globalErrors.isNotEmpty()) {
            return Result.failure(ErrorException(globalErrors))
        }

        return userRepository.register(user)
    }

    private fun validateUser(user: User, globalErrors: MutableList<GlobalError>) {
        if (!isValidEmail(user.email)) {
            globalErrors.add(GlobalError(100, ErrorCode.CODE_100.description))
        }


        if (!isValidPassword(user.password)) {
            globalErrors.add(GlobalError(101, ErrorCode.CODE_101.description))
        }
    }


}