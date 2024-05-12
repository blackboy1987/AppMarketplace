package com.cqzyzx.jpfx.entity


class HomeCategory(
    val id: Int,
    val name: String
)
class HomeCarousel(
    val id: Int,
    val image: String,
    val url: String,
)
class HomeNotice(
    val id: Int,
    val title: String,
    val content: String,
)
data class HomeEntity(
    val categories: List<HomeCategory> = listOf(),
    val carousel: List<HomeCarousel> = listOf(),
    var notice0: List<HomeNotice> = listOf(),
    var notice1: List<HomeNotice> = listOf(),
    var list: List<SoftEntity> = listOf(),
)

data class HomeEntityResponse(val data: HomeEntity) : BaseResponse()
