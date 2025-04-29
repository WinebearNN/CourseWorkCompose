package com.hse.courseworkcompose.data.network.request

data class UserRequest(
    var email: String = "",
    var password: String = "",
    var name:String="",
    var surname:String="",
    var phoneNumber:String="",
    var dob:Long=0
)