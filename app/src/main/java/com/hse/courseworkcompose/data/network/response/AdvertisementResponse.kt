package com.hse.courseworkcompose.data.network.response

data class AdvertisementResponse (
    var id:Long,
    var price: Int,
    var isFavorite: Boolean,
    var sellerDiscount: Float,
    var url: MutableList<String>,
    var brand: String,
    var name: String,
    var description:String,
    var popularity:Float,
    var quantityReviews:Int,
    var category: String

)