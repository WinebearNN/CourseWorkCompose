package com.hse.courseworkcompose.domain.useCase

import com.hse.courseworkcompose.domain.repository.UserRepository
import javax.inject.Inject
import com.hse.courseworkcompose.util.ErrorCode
import com.hse.courseworkcompose.util.*


class LoginUseCase @Inject constructor(private val userRepository: UserRepository) {

    companion object {
        private const val TAG = "LoginUserUseCase"
    }

    suspend fun execute(email:String,password:String): Result<Unit> {
        val globalErrors = mutableListOf<ErrorCode>()

        validateUser(email,password, globalErrors)

        // Если есть ошибки, возвращаем их
        if (globalErrors.isNotEmpty()) {
            return Result.failure(Error(globalErrors))
        }

        // Вызов метода для входа пользователя
        return userRepository.login(email,password)
    }

    // Валидация пользователя
    private fun validateUser(email:String,password:String, globalErrors: MutableList<ErrorCode>) {
        // Валидация email
        if (!CheckValidation.Companion.isValidEmail(email)) {
            globalErrors.add(ErrorCode.ERROR_101)
        }

        // Валидация пароля
        if (!CheckValidation.Companion.isValidPassword(password)) {
            globalErrors.add(ErrorCode.ERROR_102)
        }
    }


}