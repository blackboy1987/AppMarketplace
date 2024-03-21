package com.bootx.app.entity

data class CategoryEntity(
    val id: Int,
    val name: String,
)

data class CategoryListResponse(val data: List<CategoryEntity>) : BaseResponse()

data class CategoryDetailResponse(val data: CategoryEntity?) : BaseResponse()