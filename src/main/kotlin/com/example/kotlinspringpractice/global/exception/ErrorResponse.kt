package com.example.kotlinspringpractice.global.exception

/**
 * 에러 응답을 표현하는 데이터 클래스
 *
 * @property status HTTP 상태 코드
 * @property code 에러 코드
 * @property message 에러에 대한 설명 메시지
 */
data class ErrorResponse(
    val status: Int,
    val code: String,
    val message: String,
)