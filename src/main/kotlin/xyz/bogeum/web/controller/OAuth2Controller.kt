package xyz.bogeum.web.controller

import com.test.springkotlin.response.CommonResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import xyz.backup_isedol_clip.makeResp
import xyz.bogeum.enum.LoginPlatform
import xyz.bogeum.enum.RespCode
import xyz.bogeum.util.logger
import xyz.bogeum.util.third_party_api.GoogleApi
import xyz.bogeum.web.service.AccountServ
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/oauth")
class OAuth2Controller(
    private val googleApi: GoogleApi,
    private val accountServ: AccountServ
) {

    private val log = logger()

    @GetMapping("/google")
    fun oauth2Google(req: HttpServletRequest, resp: HttpServletResponse, code: String)
    : ResponseEntity<CommonResponse> {
        val accessToken = googleApi.getAccessToken(code)
        val email = googleApi.getEmail(accessToken)

        val user = accountServ.socialLogin(resp, email, LoginPlatform.GOOGLE)
        log.info("email: ${user.email}")

        return makeResp(RespCode.OK.status, RespCode.OK.desc, user)
    }
}
