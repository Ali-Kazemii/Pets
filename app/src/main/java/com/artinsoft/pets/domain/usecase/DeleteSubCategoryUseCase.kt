package com.artinsoft.pets.domain.usecase

import com.artinsoft.pets.data.local.SubCategoryItem
import com.artinsoft.pets.domain.repository.CategoryRepository
import com.artinsoft.pets.utils.RequestFlowUseCase
import javax.inject.Inject

class DeleteSubCategoryUseCase @Inject constructor(
    private val repository: CategoryRepository
): RequestFlowUseCase<SubCategoryItem>(){

    override suspend fun run(item: SubCategoryItem) {
        repository.deleteSubCategoryItem(item)
    }
}