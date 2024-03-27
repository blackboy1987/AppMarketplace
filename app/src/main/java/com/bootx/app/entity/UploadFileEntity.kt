package com.bootx.app.entity

open class UploadFileEntity {
    var url: String = ""
}

data class UploadFileResponse(val data: UploadFileEntity) : BaseResponse()