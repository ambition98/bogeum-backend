package xyz.bogeum.web.service

import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import xyz.bogeum.auth.JwtProvider
import xyz.bogeum.enum.LoginPlatform
import xyz.bogeum.exception.DataNotFoundException
import xyz.bogeum.util.logger
import xyz.bogeum.web.entity.AccountEntity
import xyz.bogeum.web.entity.UserPwEntity
import xyz.bogeum.web.model.AccountDto
import xyz.bogeum.web.model.req.Signup
import xyz.bogeum.web.repository.AccountRepo
import xyz.bogeum.web.repository.UserPwRepo
import java.util.UUID
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Service
class AccountServ(
    private val accountRepo: AccountRepo,
    private val userPwRepo: UserPwRepo,
    private val jwtProvider: JwtProvider
) {
    private final val log = logger()
    private final val passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()

    fun getByEmail(email: String) = accountRepo.findByEmail(email)

    fun getById(id: UUID) = try { accountRepo.findById(id).get() } catch (e: DataNotFoundException) { null }

    fun existsByEmail(email: String) = accountRepo.existsByEmail(email)

    fun socialLogin(resp: HttpServletResponse, email: String, loginPlatform: LoginPlatform): AccountDto {
        val entity = accountRepo.findByEmail(email)
            ?: AccountEntity(email = email, loginPlatform = loginPlatform)
        entity.refreshToken = jwtProvider.makeRefreshToken(entity.id)

        val result = accountRepo.save(entity)
        jwtProvider.setTokenToCookie(resp, result.id)

        return AccountDto.build(result)
    }

    fun logout(req: HttpServletRequest, resp: HttpServletResponse) {
        jwtProvider.deleteTokenAtCookie(req, resp)
        val jwt = jwtProvider.getTokenFromCookie(req) ?: return
        println("inLogout(), jwt: $jwt")
        val entity = getById(UUID.fromString(jwtProvider.getId(jwt))) ?: return
        accountRepo.save(entity.also { it.refreshToken = null })
    }

    @Transactional
    fun signup(signup: Signup): AccountEntity {
        val digest = passwordEncoder.encode(signup.password)
        log.info("digest: $digest")
        val accountEntity = AccountEntity(email = signup.email, loginPlatform = LoginPlatform.BOGEUM)
            .also {
                it.refreshToken = jwtProvider.makeRefreshToken(it.id)
            }
        val accountRes = accountRepo.save(accountEntity)

        val userPwEntity = UserPwEntity(accountRes.id, digest)
        userPwRepo.save(userPwEntity)

        return accountRes
    }
}