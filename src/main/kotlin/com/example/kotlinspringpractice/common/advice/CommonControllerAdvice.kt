package com.example.kotlinspringpractice.common.advice

import org.springframework.core.MethodParameter
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.web.ErrorResponse
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice

/**
 * 전역적으로 모든 컨트롤러에서 반환되는 응답을 가로채서 처리
 */
@RestControllerAdvice
class CommonControllerAdvice : ResponseBodyAdvice<Any> {

    /**
     * 어떤 응답이 가로채져야 하는지 결정
     */
    override fun supports(
        returnType: MethodParameter,
        converterType: Class<out HttpMessageConverter<*>>
    ): Boolean {
        return returnType.parameterType != Void.TYPE
    }

    /**
     * 컨트롤러가 반환한 응답 본문을 실제로 가로채서 처리
     */
    override fun beforeBodyWrite(
        body: Any?,
        returnType: MethodParameter,
        selectedContentType: MediaType,
        selectedConverterType: Class<out HttpMessageConverter<*>>,
        request: ServerHttpRequest,
        response: ServerHttpResponse
    ): Any? {
        return if (isErrorResponse(body)) {
            val errorResponses = body as List<*>
            wrapResponse(errorResponses, (errorResponses.first() as ErrorResponse).statusCode)
        } else {
            wrapResponse(body, HttpStatus.OK)
        }
    }

    private fun isErrorResponse(body: Any?): Boolean {
        return body is List<*> && body.isNotEmpty() && body.first() is ErrorResponse
    }

    private fun wrapResponse(response: Any?, status: HttpStatusCode): com.example.kotlinspringpractice.common.exception.ErrorResponse {
        return CommonWrapperResponse(
            status = status.value(),
            data = response
        )
    }
}