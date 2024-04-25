package com.bootx.app.entity

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SettingEntity(
    val name: String,
    val logo: String,
)

