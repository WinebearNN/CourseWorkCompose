package com.hse.courseworkcompose.data.network.request

data class OrderRequest (
    var globalId:Long=0,
    var userId:Long=0,
    var idAdvertisement: Long=0,
    var amount:Int = 0,
    var address:String="",
    var isByCash: Boolean = true,

)