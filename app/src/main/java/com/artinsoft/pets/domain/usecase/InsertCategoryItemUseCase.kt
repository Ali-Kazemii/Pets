package com.artinsoft.pets.domain.usecase

import com.artinsoft.pets.data.local.CategoryItem
import com.artinsoft.pets.domain.model.CategoryModel
import com.artinsoft.pets.domain.model.InvalidCategoryException
import com.artinsoft.pets.domain.repository.CategoryRepository
import com.artinsoft.pets.utils.Constants
import com.artinsoft.pets.utils.RequestFlowUseCase
import javax.inject.Inject

class InsertCategoryItemUseCase @Inject constructor(
    private val repository: CategoryRepository
) : RequestFlowUseCase<CategoryModel>() {

    @Throws(InvalidCategoryException::class)
    override suspend fun run(item: CategoryModel) {
        if (item.name.isNullOrEmpty() || item.id == null)
            throw InvalidCategoryException("The fields are null or empty.")

        if (item.name.length > Constants.MAX_CATEGORY_NAME_LENGTH) {
            throw InvalidCategoryException(
                ("The name of category " +
                        "must not exceed ${Constants.MAX_CATEGORY_NAME_LENGTH} characters")
            )
        }

        val categoryItem = CategoryItem(item.id, item.name)
        repository.insertCategoryItem(categoryItem)
    }
}