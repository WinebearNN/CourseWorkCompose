package com.hse.courseworkcompose.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hse.courseworkcompose.domain.entity.User
import com.hse.courseworkcompose.domain.useCase.LoginUseCase
import com.hse.courseworkcompose.utills.ErrorException
import com.hse.courseworkcompose.utills.GlobalError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    companion object {
        private const val TAG = "LoginViewModel"
    }

    private val _globalErrors = MutableStateFlow<List<GlobalError>?>(null)
    val globalErrors: StateFlow<List<GlobalError>?> = _globalErrors

    private val _loginResult = MutableStateFlow<Result<Unit>?>(null)
    val loginResult: StateFlow<Result<Unit>?> = _loginResult

    fun signInUser(email: String, password: String) {
        val user = User(email = email, password = password)

        viewModelScope.launch {
            val result = loginUseCase.execute(user)
            handleSignInResult(result)
        }
    }

    private fun handleSignInResult(result: Result<User>) {
        result.onSuccess {
            _loginResult.value = Result.success(Unit)
        }.onFailure { exception ->
            if (exception is ErrorException) {
                _globalErrors.value = exception.errors
            } else {
                _loginResult.value = Result.failure(exception)
            }
        }
    }

}