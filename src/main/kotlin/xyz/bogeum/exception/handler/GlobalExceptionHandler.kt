package xyz.bogeum.exception.handler

import com.test.springkotlin.response.CommonResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import xyz.backup_isedol_clip.makeResp
import xyz.bogeum.exception.GoogleApiException
import xyz.bogeum.exception.IncorrectLoginInfoException
import xyz.bogeum.exception.OAuthAuthorityDeniedException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.validation.ConstraintViolationException

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

    @ExceptionHandler(
        GoogleApiException::class,
        ConstraintViolationException::class,
        IncorrectLoginInfoException::class
    )
    fun badRequestHandler(e: Exception): ResponseEntity<CommonResponse> {
        return makeResp(HttpStatus.BAD_REQUEST, e.message!!)
    }


}