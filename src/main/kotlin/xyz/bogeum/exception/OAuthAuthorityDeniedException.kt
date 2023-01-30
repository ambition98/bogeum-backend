package xyz.bogeum.exception

import xyz.bogeum.util.logger

class OAuthAuthorityDeniedException(msg: String) : Exception(msg) {
    init {
        val log = logger()
        log.warn(msg)
    }
}