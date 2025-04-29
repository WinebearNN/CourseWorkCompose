package com.hse.courseworkcompose.util


enum class ErrorCode(val value:Int ,val translation: String) {
    ERROR_101(101,"Неправильный формат email"),
    ERROR_102(102,"Пароль должен быть длиной от 6 символов"),
    ERROR_103(103,"Номер телефона должен содержать минимум 10 цифр"),

    ERROR_401(401,"Неправильно введен email или password"),

    ERROR_520(501,"Неопознанная ошибка");



    companion object {
        fun fromValue(value: Int): ErrorCode? {
            return ErrorCode.entries.find { it.value == value }
        }

        fun fromTranslation(translation: String): ErrorCode? {
            return ErrorCode.entries.find { it.translation == translation }
        }
    }
}

