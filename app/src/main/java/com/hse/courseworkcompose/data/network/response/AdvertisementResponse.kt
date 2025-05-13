package com.hse.courseworkcompose.data.network.response

data class AdvertisementResponse (
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