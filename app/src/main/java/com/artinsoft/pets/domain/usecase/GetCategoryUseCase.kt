package com.artinsoft.pets.domain.usecase

import com.artinsoft.pets.data.local.CategoryItem
import com.artinsoft.pets.domain.repository.CategoryRepository
import com.artinsoft.pets.utils.ResponseFlowUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoryUseCase @Inject constructor(
    private val repository: CategoryRepository
): ResponseFlowUseCase<List<CategoryItem>>(){

    override fun run(): Flow<List<CategoryItem>> {
        return repository.observeAllCategoryItems()
    }
}