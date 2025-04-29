package com.hse.courseworkcompose.data.network.response

import com.hse.courseworkcompose.domain.entity.Country

data class UserResponse(
    var globalId: Long = 0,
    var email: String = "",
    var password: String = "",
    var name: String = "",
    var surname:String = "",
    var phoneNumber:String ="",
    var dob: Long = 0,
    var country: Country = Country.Russia
)

