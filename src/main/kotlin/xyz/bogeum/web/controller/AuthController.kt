package xyz.bogeum.web.controller

import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import xyz.backup_isedol_clip.makeResp
import xyz.bogeum.auth.JwtProvider
import xyz.bogeum.enum.RespCode
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
    fun login(@RequestBody login: LoginDto, resp:HttpServletResponse)
    = makeResp(RespCode.OK.status, RespCode.OK.desc, accountServ.login(login, resp))

    @PostMapping("/signup")
    fun signup(@RequestBody signup: LoginDto, resp: HttpServletResponse)
    = makeResp(RespCode.OK.status, RespCode.OK.desc, accountServ.signup(signup, resp))

    @GetMapping("/exists")
    fun checkEmailDuplicated(@Email @NotBlank email: String, resp: HttpServletResponse)
    = if (accountServ.existsByEmail(email))
        makeResp(RespCode.AVAILABLE_EMAIL.status, RespCode.AVAILABLE_EMAIL.desc)
    else
        makeResp(RespCode.DUPLICATED_EMAIL.status, RespCode.DUPLICATED_EMAIL.desc)
}

