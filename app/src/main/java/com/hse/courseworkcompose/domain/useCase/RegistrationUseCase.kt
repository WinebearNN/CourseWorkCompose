package com.hse.courseworkcompose.domain.useCase

import com.hse.courseworkcompose.domain.repository.UserRepository
import com.hse.courseworkcompose.util.CheckValidation
import com.hse.courseworkcompose.util.Error
import com.hse.courseworkcompose.util.ErrorCode
import javax.inject.Inject

class RegistrationUseCase @Inject constructor(private val userRepository: UserRepository) {

    companion object {
        private const val TAG = "RegisterUserUseCase"
    }

    suspend fun execute(email: String,password: String,name:String,phoneNumber:String): Result<Unit> {
        val globalErrors = mutableListOf<ErrorCode>()

        validateUser(email=email,password=password, phoneNumber = phoneNumber,globalErrors)

        if (globalErrors.isNotEmpty()) {
            return Result.failure(Error(globalErrors))
        }

        return userRepository.register(
            email=email,
            password=password,
            name=name,
            phoneNumber=phoneNumber
        )
    }

    private fun validateUser(email:String,password:String,phoneNumber: String, globalErrors: MutableList<ErrorCode>) {
        if (!CheckValidation.Companion.isValidEmail(email)) {
            globalErrors.add(ErrorCode.ERROR_101)
        }

        // Валидация пароля
        if (!CheckValidation.Companion.isValidPassword(password)) {
            globalErrors.add(ErrorCode.ERROR_102)
        }

        if (!CheckValidation.Companion.isValidPhoneNumber(phoneNumber = phoneNumber)) {
            globalErrors.add(ErrorCode.ERROR_103)
        }
    }



}