package com.hse.courseworkcompose.presentation.viewmodel.logIn

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hse.courseworkcompose.domain.useCase.LoginUseCase
import com.hse.courseworkcompose.domain.useCase.RegistrationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.hse.courseworkcompose.util.Error
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registrationUseCase: RegistrationUseCase
) : ViewModel() {

    companion object {
        private const val TAG = "LoginViewModel"
    }

    private val _logInResult = MutableStateFlow<LogInResult>(LogInResult.Loading)
    val logInResult: StateFlow<LogInResult> = _logInResult.asStateFlow()

//    private val _registrationResult = MutableStateFlow<LogInResult>(LogInResult.Loading)
//    val registrationResult: StateFlow<LogInResult> = _registrationResult.asStateFlow()



    fun registerUser(email: String, password: String, name: String,phoneNumber:String) {

        viewModelScope.launch {
            _logInResult.value = LogInResult.Loading
            _logInResult.value = registrationUseCase.execute(
                email = email,
                password = password,
                name=name,
                phoneNumber=phoneNumber)
                .fold(
                    onSuccess = {
                        LogInResult.UserSuccess(true)
                    },
                    onFailure = { error ->
                        when (error) {
                            is Error -> LogInResult.ValidationError(error.errors)
                            else -> LogInResult.Error(error)
                        }
                    }
                )
        }
    }


    fun logInUser(email: String, password: String) {
        viewModelScope.launch {
            _logInResult.value = LogInResult.Loading
            _logInResult.value = loginUseCase.execute(email = email, password = password)
                .fold(
                    onSuccess = {
                        LogInResult.UserSuccess(true)
                    },
                    onFailure = { error ->
                        when (error) {
                            is Error -> LogInResult.ValidationError(error.errors)
                            else -> LogInResult.Error(error)
                        }
                    }
                )
        }
    }





}