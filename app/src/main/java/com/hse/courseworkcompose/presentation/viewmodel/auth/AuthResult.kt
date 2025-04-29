package com.hse.courseworkcompose.presentation.viewmodel.auth

import com.hse.courseworkcompose.domain.entity.User

sealed class AuthResult {
    data class UserSuccess(val user: User) : AuthResult()
    data class Error(val exception: Throwable) : AuthResult()
    object Loading : AuthResult()
}