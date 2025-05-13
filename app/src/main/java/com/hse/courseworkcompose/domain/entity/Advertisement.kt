package com.hse.courseworkcompose.domain.entity

data class AdvertisementShort(
    var globalId:Long,
    var price: Int,
    var isFavorite: Boolean,
    var sellerDiscount: Float,
    var url: String,
    var brand: String,
    var name: String
)

data class Advertisement(
    var globalId:Long,
    var price: Int,
    var isFavorite: Boolean,
    var sellerDiscount: Float,
    var url: List<String>,
    var brand: String,
    var name: String,
    var description:String,
    var rate:Float,
    var quantityReviews:Int
)
