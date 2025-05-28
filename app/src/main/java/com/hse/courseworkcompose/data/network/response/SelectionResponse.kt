package com.hse.courseworkcompose.data.network.response

import com.hse.courseworkcompose.domain.entity.User

data class SelectionResponse(
    var id: Long = 0,
    var owner: User = User(),
    var description: String = "",
    var name: String = "",
)