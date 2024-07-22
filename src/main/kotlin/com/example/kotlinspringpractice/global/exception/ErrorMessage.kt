package com.example.kotlinspringpractice.global.exception

import com.fasterxml.jackson.annotation.JsonFormat

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
enum class ErrorMessage(
    val status: Int,
    val code: String,
    val message: String
) {
    /** COMMON ERRORS */
    INVALID_REQUEST(400, "ERR001", "잘못된 요청 형식입니다."),
    UNAUTHORIZED(401, "ERR002", "인가되지 않은 접근입니다."),
    FORBIDDEN(403, "ERR003", "접근 권한이 없습니다."),
    NOT_FOUND(404, "ERR004", "리소스를 찾을 수 없습니다."),
    METHOD_NOT_ALLOWED(405, "ERR005", "허용되지 않은 메소드입니다."),
    INTERNAL_SERVER_ERROR(500, "ERR006", "내부 서버 오류입니다."),
    BAD_GATEWAY(502, "ERR007", "잘못된 게이트웨이입니다."),
    SERVICE_UNAVAILABLE(503, "ERR008", "서비스를 사용할 수 없습니다."),
    GATEWAY_TIMEOUT(504, "ERR009", "게이트웨이 시간 초과입니다."),

    /** VALIDATION ERRORS */
    INVALID_INPUT_VALID(400, "VAL001", "잘못된 입력입니다."),
    INVALID_INPUT_VALIDATED(400, "VAL001", "잘못된 입력입니다."),
    MISSING_REQUIRED_FIELD(400, "VAL003", "필수 필드가 누락되었습니다."),
    INVALID_EMAIL(400, "VAL004", "유효하지 않은 이메일 형식입니다."),
    PASSWORD_TOO_WEAK(400, "VAL005", "비밀번호가 너무 약합니다."),
    USERNAME_TAKEN(400, "VAL006", "사용자 이름이 이미 사용 중입니다."),

    /** AUTHENTICATION ERRORS */
    AUTHENTICATION_FAILED(401, "AUTH001", "인증에 실패했습니다."),
    TOKEN_EXPIRED(401, "AUTH002", "인증 토큰이 만료되었습니다."),
    INVALID_TOKEN(401, "AUTH003", "유효하지 않은 인증 토큰입니다."),

    /** AUTHORIZATION ERRORS */
    USER_NOT_AUTHORIZED(403, "AUTH004", "사용자에게 권한이 없습니다."),
    ROLE_NOT_ALLOWED(403, "AUTH005", "해당 역할이 허용되지 않았습니다."),

    /** USER ERRORS */
    USER_ALREADY_EXISTS(400, "USR001", "사용자가 이미 존재합니다."),
    USER_NOT_FOUND(404, "USR002", "사용자를 찾을 수 없습니다."),
    USER_ACCOUNT_LOCKED(403, "USR003", "사용자 계정이 잠겼습니다."),

    /** SYSTEM ERRORS */
    DATABASE_CONNECTION_FAILED(500, "SYS001", "데이터베이스 연결에 실패했습니다."),
    DATABASE_TIMEOUT(500, "SYS002", "데이터베이스 작업이 시간 초과되었습니다."),
    DATA_INTEGRITY_ERROR(500, "SYS003", "데이터 무결성 오류입니다."),
    SERVICE_DEPENDENCY_FAILED(502, "SYS004", "서비스 종속성이 실패했습니다."),
    UNABLE_TO_GENERATE_UNIQUE_ID(500, "SYS005", "고유 ID를 생성할 수 없습니다.")
}