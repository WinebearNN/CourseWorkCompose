package com.hse.courseworkcompose.presentation.viewmodel.advertisement

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hse.courseworkcompose.domain.entity.Advertisement
import com.hse.courseworkcompose.domain.entity.AdvertisementShort
import com.hse.courseworkcompose.domain.useCase.AdvertisementUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AdvertisementViewModel @Inject constructor(
    private val advertisementUseCase: AdvertisementUseCase
): ViewModel() {

    companion object {
        private const val TAG = "AdvertisementViewModel"
    }

    private val _loading = MutableStateFlow<Boolean?>(null)
    val loading: StateFlow<Boolean?> get() = _loading

    private val _list = MutableStateFlow<List<AdvertisementShort>?>(null)
    val list: StateFlow<List<AdvertisementShort>?> get() = _list

    private val _advertisement = MutableStateFlow<Advertisement?>(null)
    val advertisement: StateFlow<Advertisement?> get() = _advertisement
    private val _userGlobalId = MutableStateFlow<String>("")
    val userGlobalId: StateFlow<String> = _userGlobalId




    fun loadData(context: Context,advertisementGlobalId:Long) {
        viewModelScope.launch {
            runCatching {
                _loading.value=true
                val prefs = context.getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
                _userGlobalId.value = prefs.getString("userGlobalId", "") ?: ""
            }.onSuccess {
                getAdvertisement(advertisementGlobalId,_userGlobalId.value)
            }
        }
    }


    private fun getAdvertisement(advertisementGlobalId:Long,userGlobalID:String) {
        viewModelScope.launch {
            runCatching {
                advertisementUseCase.getAdvertisement(advertisementGlobalId,userGlobalID)
            }.onSuccess { result ->
                _advertisement.value = result.getOrNull()
            }.onFailure { exception ->
                Log.e(TAG, "Error loading advertisement from server: $exception")
            }.also {
                _loading.value=false
            }
        }
    }

    fun saveFavoriteAdvertisementShort(advertisementShort: AdvertisementShort) {
        println(_userGlobalId.value)
        viewModelScope.launch {
            runCatching {
                advertisementUseCase.saveFavoriteAdvertisement(advertisementShort=advertisementShort, userGlobalId = _userGlobalId.value)
            }.onSuccess { localResult ->
                return@onSuccess
            }.onFailure { exception ->
                Log.e(TAG, "$exception")
            }
        }
    }



    fun saveFavoriteAdvertisement() {
        viewModelScope.launch {
            runCatching {
                val advertisementShort= AdvertisementShort(
                    globalId = _advertisement.value!!.globalId,
                    name = _advertisement.value!!.name,
                    price = _advertisement.value!!.price,
                    isFavorite = true,
                    sellerDiscount = _advertisement.value!!.sellerDiscount,
                    brand = _advertisement.value!!.brand,
                    url = _advertisement.value!!.url,
                )
                advertisementUseCase.saveFavoriteAdvertisement(advertisementShort,_userGlobalId.value)
            }.onSuccess { localResult ->
                return@onSuccess
            }.onFailure { exception ->
                Log.e(TAG, "$exception")
            }
        }
    }

    fun deleteFavoriteAdvertisementShort(advertisementShort: AdvertisementShort) {
        viewModelScope.launch {
            runCatching {
                advertisementUseCase.deleteFavoriteAdvertisement(advertisementShort,_userGlobalId.value)
            }.onSuccess { localResult ->
                return@onSuccess
            }.onFailure { exception ->
                Log.e(TAG, "$exception")
            }
        }
    }

    fun deleteFavoriteAdvertisement() {
        viewModelScope.launch {
            runCatching {
                val advertisementShort= AdvertisementShort(
                    globalId = _advertisement.value!!.globalId,
                    name = _advertisement.value!!.name,
                    price = _advertisement.value!!.price,
                    isFavorite = false,
                    sellerDiscount = _advertisement.value!!.sellerDiscount,
                    brand = _advertisement.value!!.brand,
                    url = _advertisement.value!!.url,
                )
                advertisementUseCase.deleteFavoriteAdvertisement(advertisementShort,_userGlobalId.value)
            }.onSuccess { localResult ->
                return@onSuccess
            }.onFailure { exception ->
                Log.e(TAG, "$exception")
            }
        }
    }


}