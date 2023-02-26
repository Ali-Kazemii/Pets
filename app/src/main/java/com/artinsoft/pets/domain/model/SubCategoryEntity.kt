package com.artinsoft.pets.domain.model

data class SubCategoryRequest(
    val categoryId: Int?= null,
    val page: Int = 1
)

data class SubCategoryModel(
    val height: Int?= null,
    val id: String?= null,
    val url: String?= null,
    val width: Int?= null,
)