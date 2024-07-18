package com.example.kotlinspringpractice.common.annotation

/**
 * 공통 응답 처리를 위해 사용되는 애노테이션.
 * 이 애노테이션이 붙은 메서드는 AOP를 통해 응답이 자동으로 ApiResponse로 래핑된다.
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class ApiResponseWrapper
