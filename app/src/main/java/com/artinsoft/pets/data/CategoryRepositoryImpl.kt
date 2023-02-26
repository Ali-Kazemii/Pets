package com.artinsoft.pets.data

import com.artinsoft.pets.data.local.CategoryDao
import com.artinsoft.pets.data.local.CategoryItem
import com.artinsoft.pets.data.local.SubCategoryDao
import com.artinsoft.pets.data.local.SubCategoryItem
import com.artinsoft.pets.data.remote.CategoryEndPoint
import com.artinsoft.pets.domain.model.CategoryModel
import com.artinsoft.pets.domain.model.SubCategoryModel
import com.artinsoft.pets.domain.repository.CategoryRepository
import com.artinsoft.pets.utils.BaseRepositoryDelegation
import com.artinsoft.pets.utils.NetworkResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@ExperimentalCoroutinesApi
class CategoryRepositoryImpl @Inject constructor(
    private val endPoint: CategoryEndPoint,
    private val categoryDao: CategoryDao,
    private val subCategoryDao: SubCategoryDao,
) : BaseRepositoryDelegation(), CategoryRepository {

    override suspend fun getCategory(): Flow<NetworkResponse<ArrayList<CategoryModel?>>> =
        networkRequest {
            endPoint.getCategories()
        }


    override suspend fun getSubCategory(
        categoryId: Int?,
        page: Int
    ): Flow<NetworkResponse<ArrayList<SubCategoryModel?>>> =
        networkRequest {
            endPoint.getSubCategory(
                categoryId = categoryId,
                page = page
            )
        }

    override suspend fun insertCategoryItem(item: CategoryItem) {
        categoryDao.insertCategoryItem(item)
    }

    override suspend fun insertSubCategoryItem(item: SubCategoryItem) {
        subCategoryDao.insertSubCategoryItem(item)
    }

    override suspend fun deleteCategoryItem(item: CategoryItem) {
        categoryDao.deleteCategoryItem(item)
    }

    override suspend fun deleteSubCategoryItem(item: SubCategoryItem) {
        subCategoryDao.deleteSubCategoryItem(item)
    }

    override fun observeAllCategoryItems(): Flow<List<CategoryItem>>{
        return categoryDao.observeAllCategoryItems()
    }

    override fun observeAllSubCategoryItems(): Flow<List<SubCategoryItem>> {
        return subCategoryDao.observeAllSubCategoryItems()
    }
}