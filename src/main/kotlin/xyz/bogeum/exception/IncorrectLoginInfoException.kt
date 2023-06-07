package xyz.bogeum.exception

import org.springframework.http.HttpStatus

class IncorrectLoginInfoException(status: HttpStatus, msg: String) : ResponseException(status, msg)