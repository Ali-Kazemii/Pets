package com.artinsoft.pets.domain.usecase

import com.artinsoft.pets.data.local.CategoryItem
import com.artinsoft.pets.data.local.FakeCategoryRepository
import com.artinsoft.pets.utils.Constants
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class InsertCategoryItemUseCaseTest {

    private lateinit var repository: FakeCategoryRepository
    private lateinit var categoryItem: CategoryItem

    @Before
    fun setUp() {
        repository = FakeCategoryRepository()
        categoryItem = CategoryItem(
            id = 1,
            name = "clothes"
        )
    }

    @Test
    fun `check name is not blank`(){
        assertThat(categoryItem.name).isNotEmpty()
    }

    @Test
    fun `check name is not more than MAX_CATEGORY_LENGHT`(){
        assertThat(categoryItem.name.length).isLessThan(Constants.MAX_CATEGORY_NAME_LENGTH)
    }

    @Test
    fun `check insert category item`() = runBlocking{
        repository.insertCategoryItem(categoryItem)
        val items = repository.observeAllCategoryItems()
        assertThat(items.first()).contains(categoryItem)
    }
}