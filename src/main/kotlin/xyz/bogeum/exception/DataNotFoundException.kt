package xyz.bogeum.exception

import org.springframework.http.HttpStatus

class DataNotFoundException(status: HttpStatus, msg: String) : ResponseException(status, msg)