package com.cqzyzx.jpfx.entity

open class UploadFileEntity {
    var url: String = ""
}

data class UploadFileResponse(val data: UploadFileEntity) : BaseResponse()