package com.hse.courseworkcompose.utills

class CheckValidation {

    companion object {
        fun isValidEmail(email: String): Boolean {
            return email.contains("@")
        }
        fun isValidPassword(password: String): Boolean {
            return password.length >= 6
        }
    }
}