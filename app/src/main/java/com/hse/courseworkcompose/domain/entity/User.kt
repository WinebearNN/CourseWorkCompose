package com.hse.courseworkcompose.domain.entity

import io.objectbox.annotation.Convert
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.converter.PropertyConverter
import java.sql.Time

@Entity
data class User(
    @Id var id: Long = 0,
    var globalId: Long = 0,
    var email: String = "",
    var password: String = "",
    var name: String = "",
    var surname:String = "",
    var phoneNumber:String ="",
    var dob: Long = 0,
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

