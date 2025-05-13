package com.hse.courseworkcompose.domain.entity

import io.objectbox.annotation.Convert
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.converter.PropertyConverter

@Entity
data class User(
    @Id var id: Long = 0,
    var globalId: Long = 0,
    var email: String = "",
    var password: String = "",
    var name: String = "",
    var surname:String = "",
    var phoneNumber:String ="",
    var dateOfBirth: Long = 0,
    @Convert(converter = CountryConverter::class, dbType = String::class)
    var country: Country = Country.Russia
)

class CountryConverter : PropertyConverter<Country, String> {
    override fun convertToEntityProperty(databaseValue: String?): Country {
        return if (databaseValue == null) Country.Russia
        else Country.valueOf(databaseValue)
    }

    override fun convertToDatabaseValue(entityProperty: Country?): String {
        return entityProperty?.name ?: Country.Russia.name
    }
}

