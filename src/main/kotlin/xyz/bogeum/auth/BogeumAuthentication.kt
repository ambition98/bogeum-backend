package xyz.bogeum.auth

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority

class BogeumAuthentication(
    private val principal: Any,
    private val credentials: Any,
    authorities: Collection<GrantedAuthority>
) : AbstractAuthenticationToken(authorities) {

    init {
        super.setAuthenticated(true)
    }

    override fun getCredentials() = credentials
    override fun getPrincipal() = principal
}
