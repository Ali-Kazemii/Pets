package com.artinsoft.pets.domain.usecase

import com.artinsoft.pets.domain.model.SubCategoryModel
import com.artinsoft.pets.domain.model.SubCategoryRequest
import com.artinsoft.pets.domain.repository.CategoryRepository
import com.artinsoft.pets.utils.FlowUseCase
import com.artinsoft.pets.utils.NetworkResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSubCategoryFromApiUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) : FlowUseCase<SubCategoryRequest, ArrayList<SubCategoryModel?>>() {

    override suspend fun run(request: SubCategoryRequest): Flow<NetworkResponse<ArrayList<SubCategoryModel?>>> {
        return categoryRepository.getSubCategory(
            categoryId = request.categoryId,
            page = request.page
        )
    }
}