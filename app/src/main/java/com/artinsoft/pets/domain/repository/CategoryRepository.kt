package com.artinsoft.pets.domain.repository

import com.artinsoft.pets.domain.model.CategoryModel
import com.artinsoft.pets.domain.model.SubCategoryModel
import com.artinsoft.pets.utils.NetworkResponse
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun getCategory(): Flow<NetworkResponse<ArrayList<CategoryModel?>>>
    suspend fun getSubCategory(
        categoryId: Int?,
        page: Int
    ): Flow<NetworkResponse<ArrayList<SubCategoryModel?>>>

    suspend fun insertCategoryItem(item: com.artinsoft.pets.data.local.CategoryItem)
    suspend fun insertSubCategoryItem(item: com.artinsoft.pets.data.local.SubCategoryItem)
    suspend fun deleteCategoryItem(item: com.artinsoft.pets.data.local.CategoryItem)
    suspend fun deleteSubCategoryItem(item: com.artinsoft.pets.data.local.SubCategoryItem)
    fun observeAllCategoryItems(): Flow<List<com.artinsoft.pets.data.local.CategoryItem>>
    fun observeAllSubCategoryItems(): Flow<List<com.artinsoft.pets.data.local.SubCategoryItem>>
}