package com.hse.courseworkcompose.presentation.viewmodel.logIn

import com.hse.courseworkcompose.util.ErrorCode

sealed class LogInResult {
    data class UserSuccess(val success: Boolean) : LogInResult()
    data class Error(val exception: Throwable) : LogInResult()
    data class ValidationError(val errorCodes: List<ErrorCode>) : LogInResult()
    object Loading : LogInResult()
}
