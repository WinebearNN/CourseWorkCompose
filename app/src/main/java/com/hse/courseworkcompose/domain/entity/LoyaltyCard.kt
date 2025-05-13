package com.hse.courseworkcompose.domain.entity

import com.hse.courseworkcompose.R


data class LoyaltyCard(
    var globalId:Long=0,
    var loyaltyLevel:LoyaltyLevel=LoyaltyLevel.STANDARD
)


enum class LoyaltyLevel(
    private val nameValue: String,
    private val saleAmountValue: Float,
    private val drawableIdValue: Int
) {
    STANDARD("Standard", 0.05f, R.drawable.card_standart),
    IMPORTANT("Important", 0.1f, R.drawable.card_important_2),
    PREMIUM("Premium", 0.15f, R.drawable.card_premium);

    val level: String
        get() = nameValue

    val saleAmount: Float
        get() = saleAmountValue

    val drawableId: Int
        get() = drawableIdValue

}
