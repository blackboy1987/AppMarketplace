package com.cqzyzx.jpfx.entity

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SoftDetailEntity(
    var id: Int=0,
    var name: String="",
    var fullName: String="",
    val size: String="",
    var memo: String="",
    var logo: String="",
    var updateDate: String="",
    val score: String="",
    val downloads: String="",
    var images: List<String> = listOf(),
    var versionName: String="",
    var versionCode: String="",
    var introduce: String="",
    var donationMember: Int=0,
    var donationIcon: Int = 0,
    var reviewCount: Int = 0,
    var updatedContent: String = "",
    var packageName: String = "",
    var targetSdkVersion: String="",
    var minSdkVersion: String="",
    var isCollect:Boolean = false,
)

data class SoftDetailResponse(val data: SoftDetailEntity) : BaseResponse()