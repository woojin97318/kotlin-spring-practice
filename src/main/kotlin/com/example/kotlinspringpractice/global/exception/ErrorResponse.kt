package com.example.kotlinspringpractice.global.exception

import java.time.LocalDateTime

/**
 * 에러 응답을 표현하는 데이터 클래스
 */
data class ErrorResponse(
    val timestamp: LocalDateTime = LocalDateTime.now(), // 응답 생성 일시
    val requestDetails: String,                         // 요청 경로와 메서드
    val status: Int,                                    // HTTP 상태 코드
    val code: String,                                   // 예외 코드
    val message: String,                                // 메시지
)