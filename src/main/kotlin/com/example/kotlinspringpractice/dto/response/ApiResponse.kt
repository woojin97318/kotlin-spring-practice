package com.example.kotlinspringpractice.dto.response

/**
 * 공통 응답 형식을 정의하는 클래스.
 */
data class ApiResponse<T>(
    val status: Int,
    val message: String,
    val data: T?
)
