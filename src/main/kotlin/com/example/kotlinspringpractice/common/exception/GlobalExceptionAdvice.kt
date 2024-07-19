package com.example.kotlinspringpractice.common.exception

import jakarta.validation.ConstraintViolationException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.NoHandlerFoundException

/**
 * 전역 예외 처리를 위한 RestControllerAdvice
 */
@RestControllerAdvice
class GlobalExceptionAdvice {

    /**
     * 사용자 정의 예외(CustomException) 처리
     * 해당 예외가 발생할 경우, 미리 정의된 상태 코드와 메시지로 응답을 반환
     */
    @ExceptionHandler(CustomException::class)
    fun handleCustomException(e: CustomException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            status = e.status,
            code = e.code,
            message = e.message
        )
        return ResponseEntity(errorResponse, HttpStatus.valueOf(e.status))
    }

    /**
     * 유효성 검사 예외(MethodArgumentNotValidException) 처리
     * 잘못된 입력이나 필수 필드 누락으로 인한 예외 처리
     * @Valid에 의한 예외
     */
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(e: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val errorMessages = e.bindingResult.fieldErrors.joinToString(", ") { fieldError ->
            "${fieldError.field}: ${fieldError.defaultMessage}"
        }

        val errorResponse = ErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            code = "ERR_VALIDATION",
            message = "유효성 검사 오류: $errorMessages"
        )

        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    /**
     * 제약 조건 위반 예외(ConstraintViolationException) 처리
     * 유효성 검사를 위반했을 때 발생하는 예외에 대한 400 응답을 반환
     * @Validated에 의한 예외
     */
    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolationException(e: ConstraintViolationException): ResponseEntity<ErrorResponse> {
        val errorMessages = e.constraintViolations.joinToString(", ") { it.message }

        val errorResponse = ErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            code = "ERR_CONSTRAINT_VIOLATION",
            message = "제약 조건 위반: $errorMessages"
        )

        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    /**
     * 존재하지 않는 엔드포인트(NoHandlerFoundException) 처리
     * 잘못된 URL 요청에 대한 404 응답을 반환
     */
    @ExceptionHandler(NoHandlerFoundException::class)
    fun handleNoHandlerFound(e: NoHandlerFoundException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            status = HttpStatus.NOT_FOUND.value(),
            code = "ERR_NOT_FOUND",
            message = "요청한 리소스를 찾을 수 없습니다."
        )

        return ResponseEntity(errorResponse, HttpStatus.NOT_FOUND)
    }

    /**
     * 데이터 무결성 예외(DataIntegrityViolationException) 처리
     * 데이터베이스에서 발생하는 무결성 위반 예외에 대한 처리
     */
    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleDataIntegrityViolation(e: DataIntegrityViolationException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            status = HttpStatus.CONFLICT.value(),
            code = "ERR_DATA_INTEGRITY",
            message = "데이터 무결성 위반: ${e.message}"
        )

        return ResponseEntity(errorResponse, HttpStatus.CONFLICT)
    }

    /**
     * 권한 거부(AccessDeniedException) 예외 처리
     * 접근 권한이 없는 경우에 대한 403 응답을 반환
     */
    @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDeniedException(e: AccessDeniedException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            status = HttpStatus.FORBIDDEN.value(),
            code = "ERR_ACCESS_DENIED",
            message = "접근이 거부되었습니다: ${e.message}"
        )

        return ResponseEntity(errorResponse, HttpStatus.FORBIDDEN)
    }

    /**
     * 모든 유형의 일반적인 예외 처리
     * 예상치 못한 예외가 발생했을 때 내부 서버 오류로 응답
     */
    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            code = "ERR_UNKNOWN",
            message = "알 수 없는 오류가 발생했습니다: ${e.message}"
        )

        return ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}