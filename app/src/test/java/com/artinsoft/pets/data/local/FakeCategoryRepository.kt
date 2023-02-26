package com.artinsoft.pets.data.local

import com.artinsoft.pets.domain.model.CategoryModel
import com.artinsoft.pets.domain.model.SubCategoryModel
import com.artinsoft.pets.domain.repository.CategoryRepository
import com.artinsoft.pets.utils.BaseRepositoryDelegation
import com.artinsoft.pets.utils.NetworkResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeCategoryRepository : BaseRepositoryDelegation(), CategoryRepository {

    private val categoryList = arrayListOf<CategoryItem>()
    private val subCategoryList = arrayListOf<SubCategoryItem>()

    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }

    override suspend fun getCategory(): Flow<NetworkResponse<ArrayList<CategoryModel?>>> = flow {
        if (shouldReturnNetworkError)
            emit(NetworkResponse.Failure(Exception("Error, category couldn't get from api")))
        else
            emit(NetworkResponse.Success(arrayListOf(CategoryModel(1, "test"))))
    }

    override suspend fun getSubCategory(
        categoryId: Int?,
        page: Int
    ): Flow<NetworkResponse<ArrayList<SubCategoryModel?>>> = flow {
        if (shouldReturnNetworkError)
            emit(NetworkResponse.Failure(Exception("Error, sub category couldn't get from api")))
        else
            emit(
                NetworkResponse.Success(
                    arrayListOf(
                        SubCategoryModel(
                            height = 10,
                            id = "1",
                            url = "testUrl",
                            width = 10
                        )
                    )
                )
            )
    }

    override suspend fun insertCategoryItem(item: CategoryItem) {
        categoryList.add(item)
    }

    override suspend fun insertSubCategoryItem(item: SubCategoryItem) {
        subCategoryList.add(item)
    }

    override suspend fun deleteCategoryItem(item: CategoryItem) {
        categoryList.remove(item)
    }

    override suspend fun deleteSubCategoryItem(item: SubCategoryItem) {
        subCategoryList.remove(item)
    }

    override fun observeAllCategoryItems(): Flow<List<CategoryItem>> {
        return flow { emit(categoryList) }
    }

    override fun observeAllSubCategoryItems(): Flow<List<SubCategoryItem>> {
        return flow { emit(subCategoryList) }
    }
}