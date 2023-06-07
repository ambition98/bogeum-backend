package xyz.bogeum.exception

import org.springframework.http.HttpStatus
import xyz.bogeum.util.logger

class GoogleApiException(status: HttpStatus, msg: String) : ResponseException(status, msg) {
    init {
        val log = logger()
        log.warn(msg)
    }
}