package com.hse.courseworkcompose.presentation.viewmodel.home

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hse.courseworkcompose.data.network.response.AdvertisementResponse
import com.hse.courseworkcompose.domain.useCase.AdvertisementUseCase
import com.hse.courseworkcompose.presentation.viewmodel.order.OrderViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val advertisementUseCase: AdvertisementUseCase
) : ViewModel() {

    companion object {
        private const val TAG = "HomeViewModel"
    }


    private val _userGlobalId = MutableStateFlow<String>("")
    val userGlobalId: StateFlow<String> = _userGlobalId

    private var _loading = MutableStateFlow<Boolean?>(null)
    val loading: StateFlow<Boolean?> get() = _loading

    private var _list = MutableStateFlow<List<AdvertisementResponse>?>(null)
    val list: StateFlow<List<AdvertisementResponse>?> get() = _list



    fun getAdvertisementShortList(context: Context) {

        viewModelScope.launch {
            _loading.value = true
            runCatching {
                val prefs = context.getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
                _userGlobalId.value = prefs.getString("userGlobalId", "") ?: ""
            }.also {
                runCatching {
                    withTimeout(20_000L) {
                        advertisementUseCase.getAdvertisementList(_userGlobalId.value)
                    }
                }.onSuccess { localResult ->

                    if (localResult.isSuccess) _list.value = localResult.getOrThrow()

                }.onFailure { exception ->
                    _list.value = mutableListOf<AdvertisementResponse>()

                    Log.e(TAG, "Failed to get advertisement list: $exception")

                }.also {
                    _loading.value = false
                }
            }
        }
    }





}