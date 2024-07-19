package com.example.kotlinspringpractice.common.config

import com.example.kotlinspringpractice.common.util.ResponseUtil
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

/**
 * ApiResponseWrapper 애노테이션이 붙은 메서드의 응답을 자동으로 ApiResponse로 래핑하는 AOP 설정 클래스.
 */
@Aspect
@Component
class ApiResponseAspect {

    /**
     * ApiResponseWrapper 애노테이션이 붙은 메서드를 포인트컷으로 설정.
     */
    @Pointcut("@annotation(com.example.kotlinspringpractice.common.annotation.ApiResponseWrapper)")
    fun apiResponsePointcut() {
    }

    /**
     * 포인트컷에 해당하는 메서드의 응답을 ApiResponse로 래핑하는 어드바이스.
     * @param joinPoint AOP 조인 포인트
     * @return ApiResponse로 래핑된 응답 객체
     */
    @Around("apiResponsePointcut()")
    fun wrapApiResponse(joinPoint: ProceedingJoinPoint): Any {
        val result = joinPoint.proceed()
        return when (result) {
            is ResponseEntity<*> -> result // 이미 ResponseEntity인 경우 그대로 반환
            else -> ResponseUtil.successResponse(result)
        }
    }
}
