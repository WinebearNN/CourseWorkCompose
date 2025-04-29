package com.hse.courseworkcompose.domain.entity

import com.hse.courseworkcompose.R


enum class Country(name: String, drawableFlagId: Int) {

    Russia("Россия", R.drawable.country);


    companion object {
        fun fromName(name: String): Country? {
            return Country.entries.find { it.name == name }
        }
    }
}