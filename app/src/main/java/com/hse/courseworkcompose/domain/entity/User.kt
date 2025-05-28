package com.hse.courseworkcompose.domain.entity

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class User(
    @Id var id: Long = 0,
    var globalId: Long = 0,
    var email: String = "",
    var password: String = "",
    var name: String = "",
    var surname:String = "",
    var phoneNumber:String ="",
    var dateOfBirth: Long = 0
)


