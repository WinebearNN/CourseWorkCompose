package com.hse.courseworkcompose.presentation.viewmodel.order

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.hse.courseworkcompose.domain.entity.Advertisement
import com.hse.courseworkcompose.domain.entity.LoyaltyCard
import com.hse.courseworkcompose.domain.entity.Order
import com.hse.courseworkcompose.domain.useCase.AdvertisementUseCase
import com.hse.courseworkcompose.domain.useCase.OrderUseCase
import com.hse.courseworkcompose.domain.useCase.ProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class OrderViewModel @Inject constructor(
    private val orderUseCase: OrderUseCase,
    private val advertisementUseCase: AdvertisementUseCase,
    private val profileUseCase: ProfileUseCase,
): ViewModel() {

    companion object{
        private const val TAG="OrderViewModel"
    }

    private val _loyaltyCard = MutableStateFlow<LoyaltyCard?>(LoyaltyCard())
    val loyaltyCard: StateFlow<LoyaltyCard?> get() = _loyaltyCard

    private val _loadingCard = MutableStateFlow<Boolean?>(null)
    val loadingCard: StateFlow<Boolean?> get() = _loadingCard

    private val _list = MutableStateFlow<List<Order>?>(null)
    val list: StateFlow<List<Order>?> get() = _list

    private val _loadingOrder = MutableStateFlow<Boolean?>(null)
    val loadingOrder: StateFlow<Boolean?> get() = _loadingOrder

    private var _loadingAdvertisement = MutableStateFlow<Boolean?>(null)
    val loadingAdvertisement: StateFlow<Boolean?> get() = _loadingAdvertisement


    private val _userGlobalId = MutableStateFlow<String>("")
    val userGlobalId: StateFlow<String> = _userGlobalId

    private val _latitude = MutableStateFlow("56.327402")
    val latitude: StateFlow<String> = _latitude

    private val _longitude = MutableStateFlow("44.007066")
    val longitude: StateFlow<String> = _longitude

    private val _address = MutableStateFlow("г.Нижний Новгород пл. Минина")
    val address: StateFlow<String> = _address



    fun getOrdersList(context: Context){
        viewModelScope.launch {
            _loadingOrder.value=true
            runCatching {
                val prefs = context.getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
                _userGlobalId.value = prefs.getString("userGlobalId", "") ?: ""
            }.also {
                runCatching {
                    orderUseCase.getAllOrdersByUserId(_userGlobalId.value)
                }.onSuccess { localResult->
                    _list.value=localResult.getOrDefault(emptyList())
                }.onFailure { e->
                    Log.d(TAG,"$e")
                    _list.value=emptyList()
                }.also {
                    _loadingOrder.value=false
                }
            }
        }
    }


    fun loadData(context: Context) {
        viewModelScope.launch {
            runCatching {
                val prefs = context.getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
                _userGlobalId.value = prefs.getString("userGlobalId", "") ?: ""
                _latitude.value = prefs.getString("latitude", "56.327402") ?: "56.327402"
                _longitude.value = prefs.getString("longitude", "44.007066") ?: "44.007066"
                _address.value = prefs.getString("address", "г.Нижний Новгород пл. Минина")
                    ?: "г.Нижний Новгород пл. Минина"
            }.also {
                getLoyaltyCard(_userGlobalId.value.toLong())
            }
        }
    }

    fun saveData(
        context: Context,
        address:String,
        longitude:String,
        latitude:String
    ) {
        _latitude.value=latitude
        _address.value=address
        _longitude.value=longitude
        val prefs = context.getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
        with(prefs.edit()) {
            putString("latitude", _latitude.value)
            putString("longitude", _longitude.value)
            putString("address", _address.value)
            apply()
        }
    }


    fun placingOrder(order:Order,navController: NavController){
        viewModelScope.launch {
            _loadingOrder.value=true
            runCatching {
                orderUseCase.placingOrder(order)
            }.also {
                _loadingOrder.value=false
                navController.popBackStack()
            }
        }
    }

    fun getLoyaltyCard(userGlobalId:Long) {
        Log.d(TAG, "6")
        viewModelScope.launch {
            _loadingCard.value = true
            runCatching {
                profileUseCase.getLoyaltyCard(userGlobalId)
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

    private val _advertisement = MutableStateFlow<Advertisement?>(null)
    val advertisement: StateFlow<Advertisement?> get() = _advertisement




    fun getAdvertisement(advertisementGlobalId:Long) {
        viewModelScope.launch {
            _loadingAdvertisement.value=true
            runCatching {
                advertisementUseCase.getAdvertisement(advertisementGlobalId = advertisementGlobalId, userId = _userGlobalId.value)
            }.onSuccess { result ->
                _advertisement.value = result.getOrNull()
            }.onFailure { exception ->
                Log.e(TAG, "Error loading advertisement from server: $exception")
            }.also {
                _loadingAdvertisement.value=false
            }
        }
    }


}