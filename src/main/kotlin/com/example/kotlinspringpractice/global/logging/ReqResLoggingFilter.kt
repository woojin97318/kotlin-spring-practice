package com.example.kotlinspringpractice.global.logging

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.ContentCachingRequestWrapper
import org.springframework.web.util.ContentCachingResponseWrapper

private val log = KotlinLogging.logger {}

/**
 * https://beaniejoy.tistory.com/97
 * 요청과 응답에 대한 필터링을 처리
 * OncePerRequestFilter를 상속받아 동일한 요청에 대해 여러 번 필터가 실행되는 것을 방지
 */
@Component
class ReqResLoggingFilter : OncePerRequestFilter() {

    /**
     * 요청과 응답을 필터링하고 로깅
     */
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        // 요청과 응답을 캐싱하여 나중에 본문을 읽을 수 있도록 함
        val cachingRequestWrapper = ContentCachingRequestWrapper(request)
        val cachingResponseWrapper = ContentCachingResponseWrapper(response)

        val startTime = System.currentTimeMillis() // 요청 시작 시간 기록

        filterChain.doFilter(cachingRequestWrapper, cachingResponseWrapper)

        val end = System.currentTimeMillis() // 요청 종료 시간 기록
        val elapsedTime = (end - startTime) / 1000.0 // 소요 시간 계산

        try {
            log.info {
                HttpLogMessage.createInstance(
                    requestWrapper = cachingRequestWrapper,
                    responseWrapper = cachingResponseWrapper,
                    elapsedTime = elapsedTime
                ).toPrettierLog()
            }

            // 응답 본문을 실제 응답에 복사
            cachingResponseWrapper.copyBodyToResponse()
        } catch (e: Exception) {
            log.error(e) { "[${this::class.simpleName}] Logging 실패: ${e.message}" }
        }
    }
}