package com.hse.courseworkcompose.presentation.viewmodel.profile

import android.content.Context
import android.net.Uri
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
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileUseCase: ProfileUseCase
) : ViewModel() {

    companion object {
        private const val TAG = "ProfileViewModel"
    }


    private val _loading = MutableStateFlow<Boolean?>(null)
    val loading: StateFlow<Boolean?> get() = _loading

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> get() = _user

    private val _loyaltyCard = MutableStateFlow<LoyaltyCard>(LoyaltyCard())
    val loyaltyCard: StateFlow<LoyaltyCard> get() = _loyaltyCard

    private val _logoutFlag = MutableStateFlow<Boolean?>(null)
    val logoutFlag: StateFlow<Boolean?> get() = _logoutFlag

    private val _loadingAvatar = MutableStateFlow<Boolean?>(null)
    val loadingAvatar: StateFlow<Boolean?> get() = _loadingAvatar


    init {
        loadLocalUserData()
        refreshUserData()
    }

    private fun getLoyaltyCard() {
        viewModelScope.launch {
            runCatching {
                _user.value.let { profileUseCase.getLoyaltyCard(it!!.globalId) }
            }.fold(
                onSuccess = { result ->
                    result.onSuccess { card ->
                        _loyaltyCard.value = card
                    }.onFailure { error ->
                        Log.e(TAG, "Server returned error: $error")
                    }
                },
                onFailure = { error ->
                    Log.e(TAG, "Network error while loading loyalty card: $error", error)
                }
            )
        }
    }

    private fun refreshUserData() {
        viewModelScope.launch {
            runCatching {
                profileUseCase.refreshUserData()
            }.onSuccess { result ->
                if (_logoutFlag.value == true) return@onSuccess
                _user.value = result.getOrThrow()
            }.onFailure { exception ->
                Log.e(TAG, "Error loading user data from server: $exception")
            }
        }
    }

    private fun loadLocalUserData() {
        viewModelScope.launch {
            runCatching {
                profileUseCase.getUser()
            }.onSuccess { localResult ->
                if (_logoutFlag.value == true) return@onSuccess
                _user.value = localResult.getOrThrow()
            }.onFailure { exception ->
                Log.e(TAG, "Error loading user data: $exception")
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

    fun updateUserData(user: User) {
        viewModelScope.launch {
            _loading.value = true
            runCatching {
                profileUseCase.updateUserData(user)
            }.onSuccess { result ->
                if (result.isSuccess) _user.value = user
            }.onFailure { exception ->
                Log.e(TAG, "Failed to update user data: $exception")
            }.also {
                _loading.value = false
            }
        }
    }

    fun uploadProfileImage(imageUri: Uri, context: Context) {
        viewModelScope.launch {
            try {
                val userId = user.value?.globalId ?: return@launch
                val imageStream = context.contentResolver.openInputStream(imageUri)
                val imageBytes = imageStream?.readBytes()
                imageStream?.close()

                if (imageBytes != null) {
                    val result = profileUseCase.uploadImageToServer(userId.toString(), imageBytes)
                    Log.i(TAG, result.toString())
                    _loadingAvatar.value = result.isSuccess
                } else {
                    _loadingAvatar.value = false
                }
            } catch (e: Exception) {
                Log.e("ProfileViewModel", "Error uploading image: ${e.message}")
                _loadingAvatar.value = false
            }
        }
    }
}