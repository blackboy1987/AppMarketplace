package com.cqzyzx.jpfx.entity

data class LoginEntity(
    val id: Int,
    val username: String,
    val avatar: String,
    val token: String,
)

data class LoginEntityResponse(val data: LoginEntity?) : BaseResponse()
