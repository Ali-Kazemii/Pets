package com.artinsoft.pets.domain.usecase

import com.artinsoft.pets.data.local.CategoryItem
import com.artinsoft.pets.domain.repository.CategoryRepository
import com.artinsoft.pets.utils.RequestFlowUseCase
import javax.inject.Inject

class DeleteCategoryUseCase @Inject constructor(
    private val repository: CategoryRepository
): RequestFlowUseCase<CategoryItem>(){

    override suspend fun run(item: CategoryItem) {
        repository.deleteCategoryItem(item)
    }
}