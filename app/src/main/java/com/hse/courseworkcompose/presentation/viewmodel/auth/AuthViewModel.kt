package com.hse.courseworkcompose.presentation.viewmodel.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hse.courseworkcompose.domain.entity.User
import com.hse.courseworkcompose.domain.useCase.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authUseCase: AuthUseCase
) : ViewModel() {


    companion object {
        private const val TAG = "AuthViewModel"
    }

    private val _authResult = MutableStateFlow<AuthResult>(AuthResult.Loading)
    val authResult: StateFlow<AuthResult> = _authResult.asStateFlow()


    fun auth() {
        viewModelScope.launch {
            _authResult.value = AuthResult.Loading
            authUseCase.execute()
                .fold(
                    onSuccess = { user ->
                        _authResult.value = AuthResult.UserSuccess(user = user)
                    },
                    onFailure = { userError ->
                        _authResult.value = AuthResult.Error(userError)
                    }
                )
        }
    }
}