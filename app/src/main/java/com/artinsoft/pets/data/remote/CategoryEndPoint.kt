package com.artinsoft.pets.data.remote

import com.artinsoft.pets.domain.model.CategoryModel
import com.artinsoft.pets.domain.model.SubCategoryModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CategoryEndPoint {
    @GET("categories")
    suspend fun getCategories(): Response<ArrayList<CategoryModel?>>

    @GET("images/search?limit=10")
    suspend fun getSubCategory(
        @Query("category_ids") categoryId: Int?,
        @Query("page") page: Int
    ): Response<ArrayList<SubCategoryModel?>>
}