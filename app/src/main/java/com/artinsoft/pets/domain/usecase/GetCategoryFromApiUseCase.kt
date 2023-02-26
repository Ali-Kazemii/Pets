package com.artinsoft.pets.domain.usecase

import com.artinsoft.pets.domain.model.CategoryModel
import com.artinsoft.pets.domain.repository.CategoryRepository
import com.artinsoft.pets.utils.FlowUseCase
import com.artinsoft.pets.utils.NetworkResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoryFromApiUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) : FlowUseCase<Unit, ArrayList<CategoryModel?>>() {

    override suspend fun run(request: Unit): Flow<NetworkResponse<ArrayList<CategoryModel?>>> {
        return categoryRepository.getCategory()
    }
}