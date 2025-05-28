package com.hse.courseworkcompose.presentation.viewmodel.profile


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hse.courseworkcompose.domain.entity.LoyaltyCard
import com.hse.courseworkcompose.domain.entity.User
import com.hse.courseworkcompose.domain.useCase.ProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(

    private val profileUseCase: ProfileUseCase
) : ViewModel() {

    companion object {
        private const val TAG = "ProfileViewModel"
    }


    private val _loadingCard = MutableStateFlow<Boolean?>(null)
    val loadingCard: StateFlow<Boolean?> get() = _loadingCard

    private val _loadingUser = MutableStateFlow<Boolean?>(null)
    val loadingUser: StateFlow<Boolean?> get() = _loadingUser

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> get() = _user

    private val _loyaltyCard = MutableStateFlow<LoyaltyCard>(LoyaltyCard())
    val loyaltyCard: StateFlow<LoyaltyCard> get() = _loyaltyCard

    private val _logoutFlag = MutableStateFlow<Boolean>(false)
    val logoutFlag: StateFlow<Boolean> get() = _logoutFlag


    init {
        val localUserResult = runCatching { loadLocalUserData() }
        if (localUserResult.isFailure) {
            Log.e(TAG, "Failed to load local user data: ${localUserResult.exceptionOrNull()}")
        }else{
            refreshUserData()
        }
    }

    fun getLoyaltyCard() {
        Log.d(TAG, "6")
        viewModelScope.launch {
            _loadingCard.value = true
            runCatching {
                _user.value.let { profileUseCase.getLoyaltyCard(it!!.globalId) }
            }.onSuccess { result ->
                result.onSuccess { card ->
                    _loyaltyCard.value = card
                }.onFailure { error ->
                    Log.e(TAG, "Server returned error: $error")
                }
            }.onFailure { error ->
                Log.e(TAG, "Network error while loading loyalty card: $error", error)
            }.also {
                _loadingCard.value = false
            }

        }
    }

    private fun refreshUserData() {
        Log.d(TAG, "4")
        viewModelScope.launch {
            runCatching {
                withTimeout(20_000L) {
                    profileUseCase.refreshUserData()
                }
            }.onSuccess { result ->
                if (_logoutFlag.value == true) return@onSuccess
                _user.value = result.getOrNull()
            }.onFailure { exception ->
                Log.e(TAG, "Error loading user data from server: $exception")
            }
        }
    }

    private fun loadLocalUserData() {
        Log.d(TAG, "2")
        viewModelScope.launch {
            _loadingUser.value = true
            runCatching {
                profileUseCase.getUser()
            }.onSuccess { localResult ->
                if (_logoutFlag.value == true) return@onSuccess
                _user.value = localResult.getOrNull()
                Log.d(TAG, localResult.getOrNull().toString())
            }.onFailure { exception ->
                Log.e(TAG, "Error loading user data: $exception")
            }.also {
                _loadingUser.value = false
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            _logoutFlag.value = true
            profileUseCase.logout()
            Log.d(TAG, "Logout")
            _logoutFlag.value = false
        }
    }


}