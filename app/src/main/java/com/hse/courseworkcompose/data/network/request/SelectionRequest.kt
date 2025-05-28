package com.hse.courseworkcompose.data.network.request

data class SelectionRequest (
    var userGlobalId:Long=0,
    var description:String="",
    var name:String="",
)