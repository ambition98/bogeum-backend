package xyz.bogeum.web.service

import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import xyz.bogeum.auth.JwtProvider
import xyz.bogeum.auth.JwtState
import xyz.bogeum.enum.LoginPlatform
import xyz.bogeum.exception.DataNotFoundException
import xyz.bogeum.exception.IncorrectLoginInfoException
import xyz.bogeum.util.logger
import xyz.bogeum.web.entity.AccountEntity
import xyz.bogeum.web.entity.UserPwEntity
import xyz.bogeum.web.model.AccountDto
import xyz.bogeum.web.model.req.LoginDto
import xyz.bogeum.web.repository.AccountRepo
import xyz.bogeum.web.repository.UserPwRepo
import java.util.*
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

//    fun getByEmail(email: String) = accountRepo.findByEmail(email)

    fun getById(id: UUID) = try { accountRepo.findById(id).get() } catch (e: DataNotFoundException) { null }

    fun existsByEmail(email: String) = accountRepo.existsByEmail(email)

    fun socialLogin(resp: HttpServletResponse, email: String, loginPlatform: LoginPlatform): AccountDto {
        val entity = accountRepo.findByEmail(email)
            ?: AccountEntity(email = email, loginPlatform = loginPlatform)

        if (entity.loginPlatform != loginPlatform)
            throw IncorrectLoginInfoException("이미 존재하는 이메일입니다")

        entity.refreshToken = jwtProvider.makeRefreshToken(entity.id)

        val result = accountRepo.save(entity)
        jwtProvider.setTokenToCookie(resp, result.id)

        return AccountDto.build(result)
    }

    fun login(login: LoginDto, resp: HttpServletResponse): AccountDto {
        val accountEntity = accountRepo.findByEmail(login.email)
            ?: throw IncorrectLoginInfoException("이메일 혹은 비밀번호가 틀립니다")

        val userPw = userPwRepo.findById(accountEntity.id).get()

        if (!passwordEncoder.matches(login.password, userPw.digest))
            throw IncorrectLoginInfoException("이메일 혹은 비밀번호가 틀립니다")

        jwtProvider.setTokenToCookie(resp, accountEntity.id)

        return AccountDto.build(accountEntity)
    }

//    fun logout(req: HttpServletRequest, resp: HttpServletResponse) {
//        jwtProvider.deleteTokenAtCookie(req, resp)
//        val jwt = jwtProvider.getTokenFromCookie(req) ?: return
//        println("inLogout(), jwt: $jwt")
//        val id = jwtProvider.getId(jwt) ?: return
//        val entity = getById(UUID.fromString(id)) ?: return
//        accountRepo.save(entity.also { it.refreshToken = null })
//    }

    fun refreshAccessToken(req: HttpServletRequest, resp: HttpServletResponse): String? {
        val jwt = jwtProvider.getTokenFromCookie(req) ?: return null
        val idString = jwtProvider.getId(jwt) ?: return null
        val id = UUID.fromString(idString)
        val entity = getById(id) ?: return null

        if (jwtProvider.getState(entity.refreshToken) == JwtState.OK) {
            return jwtProvider.makeUserToken(id)
        }

        return null
    }

    @Transactional
    fun signup(signup: LoginDto, resp: HttpServletResponse): AccountDto {
        val digest = passwordEncoder.encode(signup.password)
        log.info("digest: $digest")
        val accountEntity = AccountEntity(email = signup.email, loginPlatform = LoginPlatform.BOGEUM)
            .also {
                it.refreshToken = jwtProvider.makeRefreshToken(it.id)
            }
        val accountRes = accountRepo.save(accountEntity)

        val userPwEntity = UserPwEntity(accountRes.id, digest)
        userPwRepo.save(userPwEntity)
        jwtProvider.setTokenToCookie(resp, accountRes.id)

        return AccountDto.build(accountRes)
    }
}