package com.cqzyzx.jpfx.entity

/**
 * 广告配置
 */
data class SiteConfig(
    val siteName: String="",
    val siteLogo: String="",
    val adType0: Boolean=false,
    val adType1: Boolean=false,
    val adType2: Boolean=false,
    val adType3: Boolean=false,
    val adType4: Boolean=false,
    val adType5: Boolean=false,
    val adType6: Boolean=false,
    val memberAdType0Count: Int=100,
    val memberAdType1Count: Int=100,
    val memberAdType2Count: Int=100,
    val memberAdType3Count: Int=100,
    val memberAdType4Count: Int=100,
    val memberAdType5Count: Int=100,
    val memberAdType6Count: Int=100,
    val adType0Count: Int=100,
    val adType1Count: Int=100,
    val adType2Count: Int=100,
    val adType3Count: Int=100,
    val adType4Count: Int=100,
    val adType5Count: Int=100,
    val adType6Count: Int=100,
)