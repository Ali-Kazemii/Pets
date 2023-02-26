package com.artinsoft.pets.domain.usecase

import com.artinsoft.pets.data.local.CategoryItem
import com.artinsoft.pets.data.local.FakeCategoryRepository
import com.google.common.truth.Truth
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class DeleteCategoryUseCaseTest {

    private lateinit var repository: FakeCategoryRepository
    private lateinit var categoryItem: CategoryItem

    @Before
    fun setUp() {
        repository = FakeCategoryRepository()
        categoryItem = CategoryItem(
            id = 1,
            name = "hats"
        )
        runBlocking {
            repository.insertCategoryItem(categoryItem)
        }
    }

    @Test
    fun `check delete category item`() = runBlocking {
        repository.deleteCategoryItem(categoryItem)
        val items = repository.observeAllCategoryItems()
        Truth.assertThat(items.first()).doesNotContain(categoryItem)
    }
}