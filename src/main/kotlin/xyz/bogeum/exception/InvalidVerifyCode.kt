package xyz.bogeum.exception

import org.springframework.http.HttpStatus

class InvalidVerifyCode(status: HttpStatus, msg: String) : ResponseException(status, msg)