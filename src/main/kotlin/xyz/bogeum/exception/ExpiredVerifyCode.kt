package xyz.bogeum.exception

import org.springframework.http.HttpStatus

class ExpiredVerifyCode(status: HttpStatus, msg: String) : ResponseException(status, msg)