package com.bootx.app.entity


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
    var notice: List<HomeNotice> = listOf(),
)

data class HomeEntityResponse(val data: HomeEntity) : BaseResponse()