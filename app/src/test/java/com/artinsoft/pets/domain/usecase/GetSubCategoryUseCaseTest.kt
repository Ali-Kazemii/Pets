package com.artinsoft.pets.domain.usecase

import com.artinsoft.pets.data.local.FakeCategoryRepository
import com.artinsoft.pets.data.local.SubCategoryItem
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetSubCategoryUseCaseTest{

    private lateinit var repository: FakeCategoryRepository
    private lateinit var subCategoryItem: SubCategoryItem

    @Before
    fun setUp() {
        repository = FakeCategoryRepository()
        subCategoryItem = SubCategoryItem(
            subCategoryId = "123",
            height = 20,
            width = 15,
            url = "https://test"
        )

        runBlocking {
            repository.insertSubCategoryItem(subCategoryItem)
        }
    }


    @Test
    fun `check observe all subCategory items`() = runBlocking{
        val items = repository.observeAllSubCategoryItems()
        assertThat(items.first()).isNotEmpty()
    }
}