package com.hse.courseworkcompose.presentation.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hse.courseworkcompose.domain.entity.User
import com.hse.courseworkcompose.domain.useCase.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authUseCase: AuthUseCase
) : ViewModel() {



    companion object {
        private const val TAG = "AuthViewModel"
    }


    private val _authResult = MutableStateFlow<Result<User>?>(null)
    val authResult: StateFlow<Result<User>?> = _authResult

    fun authUser() {
        viewModelScope.launch {
            val result = authUseCase.execute()
            handleAuthResult(result)
        }
    }

    private fun handleAuthResult(result: Result<User>) {
        result.onSuccess {
            _authResult.value = Result.success(result.getOrNull()!!)
        }.onFailure { exception ->
            _authResult.value = Result.failure(exception)
        }

    }
}