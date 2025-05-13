package com.hse.courseworkcompose.domain.entity

data class Filter (
    var productColor: List<String>,
    var productPriceMin : String,
    var productPriceMax:String,
    var productMale:List<String>,
    var productSize:List<String>
)