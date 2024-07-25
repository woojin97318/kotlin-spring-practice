package com.example.kotlinspringpractice.global.logging

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.http.HttpStatus
import org.springframework.web.util.ContentCachingRequestWrapper
import org.springframework.web.util.ContentCachingResponseWrapper

/**
 * HTTP 요청 및 응답에 대한 로그 메시지
 */
data class HttpLogMessage(
    val httpMethod: String,       // HTTP 메서드 (예: GET, POST, PUT, DELETE)
    val requestUri: String,       // 요청 URI
    val httpStatus: HttpStatus,   // 응답 상태 코드
    val clientIp: String,         // 클라이언트 IP 주소
    val elapsedTime: Double,      // 요청을 처리하는 데 걸린 시간 (초 단위)
    val requestHeaders: String?,  // 요청 헤더 (nullable)
    val responseHeaders: String?, // 응답 헤더 (nullable)
    val requestParam: String?,    // 요청 파라미터 (nullable)
    val requestBody: String?,     // 요청 본문 (nullable)
    val responseBody: String?     // 응답 본문 (nullable)
) {
    companion object {
        fun createInstance(
            requestWrapper: ContentCachingRequestWrapper,
            responseWrapper: ContentCachingResponseWrapper,
            elapsedTime: Double
        ): HttpLogMessage {
            return HttpLogMessage(
                httpMethod = requestWrapper.method,
                requestUri = requestWrapper.requestURI,
                httpStatus = HttpStatus.valueOf(responseWrapper.status),
                clientIp = requestWrapper.remoteAddr,
                elapsedTime = elapsedTime,
                requestHeaders = this.getRequestHeaders(requestWrapper),
                responseHeaders = this.getResponseHeaders(responseWrapper),
                requestParam = this.getRequestParams(requestWrapper),
                requestBody = String(requestWrapper.contentAsByteArray),
                responseBody = String(responseWrapper.contentAsByteArray)
            )
        }

        /**
         * 요청 헤더를 문자열로 변환하여 반환
         */
        private fun getRequestHeaders(request: ContentCachingRequestWrapper): String {
            val headerNames = request.headerNames.toList()
            return headerNames.joinToString(separator = ", ") { headerName ->
                val headerValue = request.getHeader(headerName)
                "$headerName: $headerValue"
            }
        }

        /**
         * 응답 헤더를 문자열로 변환하여 반환
         */
        private fun getResponseHeaders(response: ContentCachingResponseWrapper): String {
            val headerNames = response.headerNames
            return headerNames.joinToString(separator = ", ") { headerName ->
                val headerValues = response.getHeaders(headerName)
                "$headerName: ${headerValues.joinToString(", ")}"
            }
        }

        /**
         * 요청 파라미터를 JSON 문자열로 변환하여 반환
         */
        private fun getRequestParams(request: ContentCachingRequestWrapper): String {
            val paramsMap = request.parameterMap.mapValues { it.value.joinToString(", ") }
            return jacksonObjectMapper().writeValueAsString(paramsMap)
        }
    }

    fun toPrettierLog(): String {
        // |[REQUEST] ${this.httpMethod} ${this.requestUri} ${this.httpStatus} (${this.elapsedTime}s)
        return """
        |
        |[REQUEST] ${this.httpMethod} ${this.requestUri} (${this.elapsedTime}s)
        |>> CLIENT_IP: ${this.clientIp}
        |>> REQUEST_HEADERS: ${this.requestHeaders}
        |>> RESPONSE_HEADERS: ${this.requestHeaders}
        |>> REQUEST_PARAM: ${this.requestParam}
        |>> REQUEST_BODY: ${truncateBody(this.requestBody)}
        |>> RESPONSE_BODY: ${truncateBody(this.responseBody)}
        """.trimMargin()
    }

    /**
     * 요청 본문과 응답 본문이 너무 길 경우, 일정 길이로 잘라서 반환
     */
    private fun truncateBody(body: String?, maxLength: Int = 1000): String {
        return body?.take(maxLength) + if ((body?.length ?: 0) > maxLength) "..." else ""
    }
}