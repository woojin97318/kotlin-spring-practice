package com.example.kotlinspringpractice.global.exception

import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.ConstraintViolationException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.NoHandlerFoundException

/**
 * 전역적으로 예외 처리
 */
@RestControllerAdvice
class GlobalExceptionAdvice {

    private fun createErrorResponse(
        request: HttpServletRequest,
        errorMessage: ErrorMessage,
        customMessage: String? = null
    ): ErrorResponse {
        return ErrorResponse(
            requestDetails = "${request.method} ${request.requestURI}",
            status = errorMessage.status,
            code = errorMessage.code,
            message = customMessage ?: errorMessage.message
        )
    }

    /**
     * 사용자 정의 예외(CustomException) 처리
     * 해당 예외가 발생할 경우, 미리 정의된 상태 코드와 메시지로 응답을 반환
     */
    @ExceptionHandler(CustomException::class)
    fun handleCustomException(e: CustomException, request: HttpServletRequest): ErrorResponse =
        createErrorResponse(request, e.errorMessage)

    /**
     * 존재하지 않는 엔드포인트(NoHandlerFoundException) 처리
     * 잘못된 URL 요청에 대한 404 응답을 반환
     */
    @ExceptionHandler(NoHandlerFoundException::class)
    fun handleNoHandlerFound(e: NoHandlerFoundException, request: HttpServletRequest): ErrorResponse =
        createErrorResponse(request, ErrorMessage.NOT_FOUND)

    /**
     * 유효성 검사 예외(MethodArgumentNotValidException) 처리
     * 잘못된 입력이나 필수 필드 누락으로 인한 예외 처리
     * @Valid에 의한 예외
     */
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(e: MethodArgumentNotValidException, request: HttpServletRequest): ErrorResponse {
        val message = e.bindingResult.fieldErrors.joinToString(", ") { fieldError ->
            "${fieldError.field}: ${fieldError.defaultMessage}"
        }

        return createErrorResponse(
            request,
            ErrorMessage.INVALID_INPUT_VALID,
            "유효성 검사 오류: $message"
        )
    }

    /**
     * 제약 조건 위반 예외(ConstraintViolationException) 처리
     * 유효성 검사를 위반했을 때 발생하는 예외에 대한 400 응답을 반환
     * @Validated에 의한 예외
     */
    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolationException(e: ConstraintViolationException, request: HttpServletRequest): ErrorResponse {
        val message = e.constraintViolations.joinToString(", ") { it.message }

        return createErrorResponse(
            request,
            ErrorMessage.INVALID_INPUT_VALIDATED,
            "제약 조건 위반: $message"
        )
    }

    /**
     * 데이터 무결성 예외(DataIntegrityViolationException) 처리
     * 데이터베이스에서 발생하는 무결성 위반 예외에 대한 처리
     */
    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleDataIntegrityViolation(e: DataIntegrityViolationException, request: HttpServletRequest): ErrorResponse =
        createErrorResponse(request, ErrorMessage.DATA_INTEGRITY_ERROR)

    /**
     * 권한 거부(AccessDeniedException) 예외 처리
     * 접근 권한이 없는 경우에 대한 403 응답을 반환
     */
    @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDeniedException(e: AccessDeniedException, request: HttpServletRequest): ErrorResponse =
        createErrorResponse(request, ErrorMessage.FORBIDDEN)

    /**
     * 모든 유형의 일반적인 예외 처리
     * 예상치 못한 예외가 발생했을 때 내부 서버 오류로 응답
     */
    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception, request: HttpServletRequest): ErrorResponse =
        createErrorResponse(request, ErrorMessage.INTERNAL_SERVER_ERROR)
}