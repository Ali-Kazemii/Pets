package com.artinsoft.pets.domain.usecase

import com.artinsoft.pets.data.local.SubCategoryItem
import com.artinsoft.pets.domain.model.InvalidCategoryException
import com.artinsoft.pets.domain.model.SubCategoryModel
import com.artinsoft.pets.domain.repository.CategoryRepository
import com.artinsoft.pets.utils.RequestFlowUseCase
import javax.inject.Inject

class InsertSubCategoryItemUseCase @Inject constructor(
    private val repository: CategoryRepository
) : RequestFlowUseCase<SubCategoryModel>() {

    @Throws(InvalidCategoryException::class)
    override suspend fun run(item: SubCategoryModel) {
        if (item.url.isNullOrEmpty() ||
            item.id.isNullOrEmpty() ||
            item.width == null ||
            item.height == null
        )
            throw InvalidCategoryException("The items are null or empty.")

        if (!item.url.contains("http://") || !item.url.contains("https://")) {
            throw InvalidCategoryException("The url is invalid.")
        }

        if (item.width <= 0)
            throw InvalidCategoryException("The width of the item can't be zero or less.")

        if (item.height <= 0)
            throw InvalidCategoryException("The height of the item can't be zero or less.")

        val categoryItem =
            SubCategoryItem(subCategoryId = item.id, url = item.url, width = item.width, height = item.height)
        repository.insertSubCategoryItem(categoryItem)
    }
}