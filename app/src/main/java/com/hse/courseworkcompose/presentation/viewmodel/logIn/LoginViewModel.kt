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
import java.text.SimpleDateFormat
import java.util.Locale
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

    fun registerUser(
        email: String,
        password: String,
        name: String,
        surname:String,
        phoneNumber:String,
        dob:String,
    ) {

        viewModelScope.launch {
            _logInResult.value = LogInResult.Loading
            _logInResult.value = registrationUseCase.execute(
                email = email,
                password = password,
                name=name,
                surname=surname,
                phoneNumber=phoneNumber,
                dob=parseDateToMillis(dob),
            )
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


    private fun parseDateToMillis(date: String): Long {
        return try {
            if (date.matches(Regex("\\d{2}\\.\\d{2}\\.\\d{4}"))) {
                val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
                formatter.parse(date)?.time ?: 0L
            } else {
                0L
            }
        } catch (e: Exception) {
            0L
        }
    }





}