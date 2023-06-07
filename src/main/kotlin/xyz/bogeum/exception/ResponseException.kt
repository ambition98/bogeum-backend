package xyz.bogeum.exception

import org.springframework.http.HttpStatus

open class ResponseException(val status: HttpStatus, val msg: String) : Exception(msg)