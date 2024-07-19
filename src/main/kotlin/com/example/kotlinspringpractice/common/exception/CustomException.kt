package com.example.kotlinspringpractice.common.exception

class CustomException(
    val errorMessage: ErrorMessage
) : RuntimeException(errorMessage.message) {

    val status: Int
        get() = errorMessage.status

    val code: String
        get() = errorMessage.code

    override val message: String
        get() = errorMessage.message
}