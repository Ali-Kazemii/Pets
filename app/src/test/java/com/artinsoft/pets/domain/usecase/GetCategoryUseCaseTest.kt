package com.artinsoft.pets.domain.usecase

import com.artinsoft.pets.data.local.CategoryItem
import com.artinsoft.pets.data.local.FakeCategoryRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class GetCategoryUseCaseTest{

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
    fun `check observe all category items`() = runBlocking{
        val items = repository.observeAllCategoryItems()
        assertThat(items.first()).isNotEmpty()
    }
}