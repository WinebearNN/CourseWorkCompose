package com.hse.courseworkcompose.domain.entity

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id


@Entity
data class AdvertisementShort(
    @Id
    var id:Long=0,
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
    var url: String,
    var brand: String,
    var name: String,
    var description:String,
    var rate:Float,
    var quantityReviews:Int
)
