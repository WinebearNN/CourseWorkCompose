package com.hse.courseworkcompose.domain.entity

data class Order(
    var globalId: Long = 0,
    var userGlobalId: Long = 0,
    var idAdvertisement: Long = 0,
    var isByCash: Boolean = true,
    var address: String = "",
    var amount: Int = 0,
)