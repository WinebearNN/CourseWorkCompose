package com.hse.courseworkcompose.domain.entity

import com.hse.courseworkcompose.R


data class LoyaltyCard(
    var globalId:Long=0,
    var level:LoyaltyLevel=LoyaltyLevel.Standart
)


enum class LoyaltyLevel(name:String,saleAmount: Float,drawableId:Int){

    Standart("Standart",0.05f,R.drawable.card_standart),
    Important("Important",0.1f,R.drawable.card_important_2),
    Premium("Premium",0.15f,R.drawable.card_premium)

}
