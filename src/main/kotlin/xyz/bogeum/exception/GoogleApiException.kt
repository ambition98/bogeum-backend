package xyz.bogeum.exception

import xyz.bogeum.util.logger

class GoogleApiException(msg: String) : RuntimeException(msg) {
    init {
        val log = logger()
        log.warn(msg)
    }
}