package xyz.bogeum.auth

import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtParser
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter
import org.springframework.stereotype.Component
import xyz.bogeum.enum.UserRole
import xyz.bogeum.util.CookieUtil
import java.nio.charset.StandardCharsets
import java.security.Key
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtProvider(
    secret: Secret,
    val cookieUtil: CookieUtil
) {

    private val keyString: String
    private val key: Key
    private val parser: JwtParser
    //    private val accessTokenExpiryMs: Long = 1800000 //30분
    private val accessTokenExpiryMs: Long = 3000
//    private val refreshTokenExpiryMs: Long = 10000
    private val refreshTokenExpiryMs: Long = 259200000 //3일
    private val authoritiesKey = "role"
    private val cookieName = "tk"

    init {
        keyString = secret.tokenKey
        key = Keys.hmacShaKeyFor(keyString.toByteArray(StandardCharsets.UTF_8))
        parser = Jwts.parserBuilder().setSigningKey(key).build()
    }

    fun makeUserToken(id: UUID) = generateToken(id, "Access Token", UserRole.USER, accessTokenExpiryMs)

    fun makeAdminToken(id: UUID) = generateToken(id, "Access Token", UserRole.ADMIN, accessTokenExpiryMs)

    fun makeRefreshToken(id: UUID) = generateToken(id, "Refresh Token", UserRole.USER, refreshTokenExpiryMs)

    fun setTokenToCookie(resp: HttpServletResponse, id: UUID) = cookieUtil.setCookie(resp, cookieName, makeUserToken(id))

    fun makeAuthentication(token: String): Authentication {
        val claims = getTokenClaims(token)
        val authorities = listOf(SimpleGrantedAuthority(claims[authoritiesKey].toString()))
        val role = claims[authoritiesKey]
        val auth = if (role != null) {
            BogeumAuthentication(role, token, authorities)
        } else { //토큰 role 클레임이 변경되었을 경우 익명사용자인증 반환
            AnonymousAuthenticationToken(
                UUID.randomUUID().toString(), "anonymousUser",
                listOf(SimpleGrantedAuthority("ROLE_ANONYMOUS"))
            )
        }

        return BogeumAuthentication(auth, token, authorities)
    }

    fun getId(token: String): String = getTokenClaims(token).id

    fun getState(token: String?) = try {
        getTokenClaims(token ?: "") //null 일 경우 Invalid
        JwtState.OK
    } catch (e: ExpiredJwtException) { JwtState.EXPIRED
    } catch (e: Exception) { JwtState.INVALID }

    fun getTokenFromCookie(req: HttpServletRequest) = cookieUtil.getCookie(req, cookieName)?.value

    fun deleteTokenAtCookie(req: HttpServletRequest, resp: HttpServletResponse)
    = cookieUtil.deleteCookie(resp, cookieName)

    private fun generateToken(id: UUID, subject: String, role: UserRole, expiryMs: Long): String {
        val currentTime = Date()
        return Jwts.builder()
            .setSubject(subject)
            .setId(id.toString())
            .setIssuedAt(Date())
            .signWith(key, SignatureAlgorithm.HS256)
            .setExpiration(Date(currentTime.time + expiryMs))
            .claim(authoritiesKey, "ROLE_$role")
            .compact()
    }

    private fun getTokenClaims(token: String) = parser.parseClaimsJws(token).body
}