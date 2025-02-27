package com.hse.courseworkcompose.domain.entity

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.objectbox.annotation.Convert
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.converter.PropertyConverter
import io.objectbox.relation.ToMany

@Entity
data class User(
    @Id var id: Long = 0,
    var globalId: Long = 0,
    var email: String = "",
    var password: String = "",
    var userName: String = "",
    var interest: String = "",
    var link: String = "",
    @Convert(converter = FriendConverter::class, dbType = String::class)
    var friends: List<Friend> = emptyList()
)

class FriendConverter : PropertyConverter<List<Friend>, String> {
    private val gson = Gson()

    override fun convertToDatabaseValue(entityProperty: List<Friend>?): String {
        return gson.toJson(entityProperty ?: emptyList<Friend>())
    }

    override fun convertToEntityProperty(databaseValue: String?): List<Friend> {
        return if (databaseValue.isNullOrEmpty()) {
            emptyList()
        } else {
            gson.fromJson(databaseValue, object : TypeToken<List<Friend>>() {}.type)
        }
    }
}

// Конвертер для хранения Set<Int> как JSON в String
class InterestConverter : PropertyConverter<MutableSet<Int>, String> {
    private val gson = Gson()

    override fun convertToDatabaseValue(entityProperty: MutableSet<Int>?): String {
        return gson.toJson(entityProperty ?: emptySet<Int>())
    }

    override fun convertToEntityProperty(databaseValue: String?): MutableSet<Int> {
        val type = object : TypeToken<MutableSet<Int>>() {}.type
        return gson.fromJson(databaseValue ?: "[]", type) ?: mutableSetOf()
    }
}