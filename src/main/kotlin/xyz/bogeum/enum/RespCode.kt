package xyz.bogeum.enum

import org.springframework.http.HttpStatus

enum class RespCode(val status: HttpStatus, val desc: String) {
    // 200
    OK(HttpStatus.OK, "요청성공."),
    AVAILABLE_EMAIL(HttpStatus.OK, "사용가능한 이메일입니다."),
    VALID_JWT(HttpStatus.OK, "정상적인 JWT 입니다."),

    // 400
    FAIL_TO_GOOGLE_API(HttpStatus.BAD_REQUEST, "잘못된 Google API 요청입니다."),
    INVALID_LOGIN_INFO(HttpStatus.BAD_REQUEST, "이메일 혹은 비밀번호가 틀립니다."),
    EXPIRED_EMAIL_VERIFY_CODE(HttpStatus.BAD_REQUEST, "이메일 인증코드가 만료되었습니다."),
    INVALID_EMAIL_VERIFY_CODE(HttpStatus.BAD_REQUEST, "잘못된 이메일 인증코드입니다."),

    // 409
    DUPLICATED_EMAIL(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다."),


    // 500
    UNKNOWN_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 에러입니다.")
}