package com.test.springkotlin.response

class CommonResponse(var msg: String, val dto: Any?) {

    constructor(msg: String): this(msg, null) {
        this.msg = msg
    }
}