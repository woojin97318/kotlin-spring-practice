package com.example.kotlinspringpractice.common.exception

data class ErrorResponse(
    val status: Int,
    val code: String,
    val message: String
)
