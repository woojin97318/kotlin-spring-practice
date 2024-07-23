package com.example.kotlinspringpractice.global.response

import java.time.LocalDateTime

/**
 * 응답 본문을 감싸는 공통 클래스
 */
data class CommonResponse<T>(
    val timestamp: LocalDateTime = LocalDateTime.now(), // 응답 생성 일시
    val requestDetails: String,                         // 요청 경로와 메서드
    val status: Int,                                    // HTTP 상태 코드
    val message: String,                                // 응답 메시지
    val data: T?                                        // 실제 데이터
)