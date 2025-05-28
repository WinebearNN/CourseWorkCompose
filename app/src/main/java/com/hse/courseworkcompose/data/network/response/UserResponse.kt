package com.hse.courseworkcompose.data.network.response

data class UserResponse(
    var globalId: Long = 0,
    var email: String = "",
    var password: String = "",
    var name: String = "",
    var surname:String = "",
    var phoneNumber:String ="",
    var dob: Long = 0,
)

