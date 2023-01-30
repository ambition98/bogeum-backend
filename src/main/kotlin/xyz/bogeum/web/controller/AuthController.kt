package xyz.bogeum.web.controller

import com.test.springkotlin.response.CommonResponse
import org.json.JSONObject
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.DelegatingPasswordEncoder
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import xyz.backup_isedol_clip.makeResp
import xyz.bogeum.auth.JwtProvider
import xyz.bogeum.auth.JwtState
import xyz.bogeum.util.logger
import xyz.bogeum.web.model.req.Signup
import xyz.bogeum.web.service.AccountServ
import java.util.Base64
import java.util.UUID
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

@RestController
@RequestMapping("/auth")
@Validated
class AuthController(
    private val jwtProvider: JwtProvider,
    private val accountServ: AccountServ,
) {

    private val log = logger()

    @PostMapping("/logout")
    fun logout(req: HttpServletRequest, resp: HttpServletResponse) {
        jwtProvider.deleteTokenAtCookie(req, resp)
        resp.status = HttpStatus.OK.value()
    }

    @GetMapping("/refresh")
    fun reassignAccessToken(req: HttpServletRequest, resp: HttpServletResponse) {
        resp.status = HttpStatus.BAD_REQUEST.value()
        val jwt = jwtProvider.getTokenFromCookie(req) ?: return
        if (jwtProvider.getState(jwt) != JwtState.EXPIRED) return
        val id = UUID.fromString(jwtProvider.getId(jwt))
        val entity = accountServ.getById(id) ?: return

        if (jwtProvider.getState(jwt) == JwtState.EXPIRED &&
            jwtProvider.getState(entity.refreshToken) == JwtState.OK) {

            jwtProvider.setTokenToCookie(resp, id)
            resp.status = HttpStatus.OK.value()
        }
    }

    @PostMapping("/signup")
    fun signup(@RequestBody signup: Signup, req: HttpServletRequest): ResponseEntity<CommonResponse> {
        return makeResp(HttpStatus.OK, "Success", accountServ.signup(signup))
    }

    @GetMapping("/exists")
    fun checkEmailDuplicated(@Email @NotBlank email: String, resp: HttpServletResponse)
    = if (accountServ.existsByEmail(email))
        resp.status = HttpStatus.CONFLICT.value()
    else
        resp.status = HttpStatus.OK.value()
}

