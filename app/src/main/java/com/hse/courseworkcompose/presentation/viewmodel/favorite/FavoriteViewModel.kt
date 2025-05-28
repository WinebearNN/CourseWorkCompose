package com.hse.courseworkcompose.presentation.viewmodel.favorite

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hse.courseworkcompose.data.network.response.AdvertisementResponse
import com.hse.courseworkcompose.domain.entity.AdvertisementShort
import com.hse.courseworkcompose.domain.useCase.AdvertisementUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val advertisementUseCase: AdvertisementUseCase
) : ViewModel() {

    companion object {
        private const val TAG = "FavoriteViewModel"
    }

    private val _userGlobalId = MutableStateFlow<String>("")
    val userGlobalId: StateFlow<String> = _userGlobalId

    private var _loading = MutableStateFlow<Boolean?>(null)
    val loading: StateFlow<Boolean?> get() = _loading

    private var _list = MutableStateFlow<List<AdvertisementShort>?>(null)
    val list: StateFlow<List<AdvertisementShort>?> get() = _list


    private fun getAdvertisementFavorites() {
        Log.d(TAG,"try to get favorites")

        viewModelScope.launch {
            runCatching {
                advertisementUseCase.getAdvertisementFavorites(_userGlobalId.value)
            }.onSuccess { localResult ->
                _list.value = localResult.getOrThrow()
            }.onFailure {e->
                _list.value = emptyList<AdvertisementShort>()
                Log.e(TAG,"Failed to get list of favorites advertisement: ${e.message}")
            }.also {
                _loading.value=false
            }
        }
    }

    fun loadData(context: Context) {
        viewModelScope.launch {
            _loading.value = true
            runCatching {
                val prefs = context.getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
                _userGlobalId.value = prefs.getString("userGlobalId", "") ?: ""

            }.also {
                getAdvertisementFavorites()
            }
        }
    }



}