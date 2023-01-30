package xyz.backup_isedol_clip

import com.test.springkotlin.response.CommonResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

fun makeResp(httpStatus: HttpStatus, msg: String, dto: Any)
    = ResponseEntity<CommonResponse>(CommonResponse(msg, dto), httpStatus)

fun makeResp(httpStatus: HttpStatus, msg: String)
    = ResponseEntity<CommonResponse>(CommonResponse(msg), httpStatus)