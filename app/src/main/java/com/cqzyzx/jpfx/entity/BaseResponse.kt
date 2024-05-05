package com.cqzyzx.jpfx.entity

open class BaseResponse {
    var code: Int = -1
    var msg: String = "error"
}

data class CommonResponse(val data: String) : BaseResponse()