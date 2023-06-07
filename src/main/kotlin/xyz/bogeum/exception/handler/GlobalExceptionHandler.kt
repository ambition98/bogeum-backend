package xyz.bogeum.exception.handler

import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import xyz.backup_isedol_clip.makeResp
import xyz.bogeum.enum.RespCode
import xyz.bogeum.exception.OAuthAuthorityDeniedException
import xyz.bogeum.exception.ResponseException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestControllerAdvice(basePackages = ["xyz.bogeum.web.controller"])
class GlobalExceptionHandler {

    @ExceptionHandler(OAuthAuthorityDeniedException::class)
    fun oauthExceptionHandler(req: HttpServletRequest, resp: HttpServletResponse) {
        val serverName = req.serverName
        if (serverName == "localhost")
            resp.sendRedirect("http://$serverName:8080")
        else
            resp.sendRedirect("https://$serverName")
    }

    @ExceptionHandler(ResponseException::class)
    fun customExceptionHandler(e: ResponseException)
    = makeResp(e.status, e.message!!)

    @ExceptionHandler(Exception::class)
    fun unknownExceptionHandler(e: Exception)
    = makeResp(RespCode.UNKNOWN_SERVER_ERROR.status, RespCode.UNKNOWN_SERVER_ERROR.desc)

//    @ExceptionHandler(
//        GoogleApiException::class,
//        ConstraintViolationException::class,
//        IncorrectLoginInfoException::class,
//        ExpiredVerifyCode::class,
//        InvalidVerifyCode::class
//    )
//    fun badRequestHandler(e: Exception): ResponseEntity<CommonResponse> {
//        return makeResp(HttpStatus.BAD_REQUEST, e.message!!)
//    }
}