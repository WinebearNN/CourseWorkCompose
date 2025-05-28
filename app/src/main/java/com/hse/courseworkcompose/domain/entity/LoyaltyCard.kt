package com.hse.courseworkcompose.domain.entity



data class LoyaltyCard(
    var globalId:Long=0,
    var userId:Long=0,
    var loyaltyLevel:LoyaltyLevel=LoyaltyLevel.STANDARD
)


enum class LoyaltyLevel(
    private val nameValue: String,
    private val saleAmountValue: Float,
) {
    STANDARD("Standard", 0.05f),
    IMPORTANT("Important", 0.1f),
    PREMIUM("Premium", 0.15f);

    val level: String
        get() = nameValue

    val saleAmount: Float
        get() = saleAmountValue

}
