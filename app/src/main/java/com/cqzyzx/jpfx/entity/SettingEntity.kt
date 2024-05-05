package com.cqzyzx.jpfx.entity

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SettingEntity(
    val name: String,
    val logo: String,
)

