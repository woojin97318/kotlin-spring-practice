package com.example.kotlinspringpractice.common.util

import com.example.kotlinspringpractice.dto.response.ApiResponse
import org.springframework.http.ResponseEntity

object ResponseUtil {

    fun <T> successResponse(data: T): ResponseEntity<ApiResponse<T>> {
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Success",
                data = data
            )
        )
    }

    fun <T> notFoundResponse(): ResponseEntity<ApiResponse<T>> {
        return ResponseEntity
            .status(404)
            .body(
                ApiResponse(
                    status = 404,
                    message = "Not Found",
                    data = null
                )
            )
    }

    fun <T> errorResponse(status: Int, message: String): ResponseEntity<ApiResponse<T>> {
        return ResponseEntity
            .status(status)
            .body(
                ApiResponse(
                    status = status,
                    message = message,
                    data = null
                )
            )
    }

    fun successResponseWithoutData(): ResponseEntity<ApiResponse<Void>> {
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Success",
                data = null
            )
        )
    }
}