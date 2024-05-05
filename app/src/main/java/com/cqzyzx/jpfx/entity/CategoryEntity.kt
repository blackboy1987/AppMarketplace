package com.cqzyzx.jpfx.entity

data class CategoryEntity(
    val id: Int,
    val name: String,
)

data class CategoryTreeEntity(
    val id: Int,
    val name: String,
    val children: List<CategoryTreeEntity>,
)

data class CategoryListResponse(val data: List<CategoryEntity>) : BaseResponse()
data class CategoryTreeListResponse(val data: List<CategoryTreeEntity>) : BaseResponse()


data class CategoryDetailResponse(val data: CategoryEntity?) : BaseResponse()