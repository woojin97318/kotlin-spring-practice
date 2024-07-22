package com.example.kotlinspringpractice.global.exception

/**
 * 사용자 정의 예외
 */
class CustomException(
    message: String,
    val errorMessage: ErrorMessage
) : RuntimeException(message) {

    constructor(errorMessage: ErrorMessage) : this(errorMessage.message, errorMessage)
}