package com.artinsoft.pets.domain.usecase

data class CategoryUseCases(
    val getCategoryFromApiUseCase: GetCategoryFromApiUseCase,
    val getSubCategoryFromApiUseCase: GetSubCategoryFromApiUseCase,
    val getCategoryUseCase: GetCategoryUseCase,
    val getSubCategoryUseCase: GetSubCategoryUseCase,
    val insertCategoryItemUseCase: InsertCategoryItemUseCase,
    val insertSubCategoryItemUseCase: InsertSubCategoryItemUseCase,
    val deleteCategoryUseCase: DeleteCategoryUseCase,
    val deleteSubCategoryUseCase: DeleteSubCategoryUseCase
)