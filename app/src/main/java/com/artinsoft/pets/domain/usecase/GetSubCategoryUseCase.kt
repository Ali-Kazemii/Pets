package com.artinsoft.pets.domain.usecase

import com.artinsoft.pets.data.local.SubCategoryItem
import com.artinsoft.pets.domain.repository.CategoryRepository
import com.artinsoft.pets.utils.ResponseFlowUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSubCategoryUseCase @Inject constructor(
    private val repository: CategoryRepository
): ResponseFlowUseCase<List<SubCategoryItem>>(){

    override fun run(): Flow<List<SubCategoryItem>> {
        return repository.observeAllSubCategoryItems()
    }
}