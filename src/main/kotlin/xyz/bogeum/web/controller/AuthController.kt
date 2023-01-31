package xyz.bogeum.web.controller

import com.test.springkotlin.response.CommonResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import xyz.backup_isedol_clip.makeResp
import xyz.bogeum.auth.JwtProvider
import xyz.bogeum.util.logger
import xyz.bogeum.web.model.req.LoginDto
import xyz.bogeum.web.service.AccountServ
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
        jwtProvider.deleteTokenAtCookie(resp)
        resp.status = HttpStatus.OK.value()
    }

    @PostMapping("/login")
    fun login(@RequestBody login: LoginDto, resp:HttpServletResponse): ResponseEntity<CommonResponse>
    = makeResp(HttpStatus.OK, "Success", accountServ.login(login, resp))

    @PostMapping("/signup")
    fun signup(@RequestBody signup: LoginDto, resp: HttpServletResponse): ResponseEntity<CommonResponse> {
        return makeResp(HttpStatus.OK, "Success", accountServ.signup(signup, resp))
    }

    @GetMapping("/exists")
    fun checkEmailDuplicated(@Email @NotBlank email: String, resp: HttpServletResponse)
    = if (accountServ.existsByEmail(email))
        resp.status = HttpStatus.CONFLICT.value()
    else
        resp.status = HttpStatus.OK.value()
}

