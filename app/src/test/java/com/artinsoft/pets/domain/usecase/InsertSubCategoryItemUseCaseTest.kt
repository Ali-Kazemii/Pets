package com.artinsoft.pets.domain.usecase

import com.artinsoft.pets.data.local.FakeCategoryRepository
import com.artinsoft.pets.data.local.SubCategoryItem
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class InsertSubCategoryItemUseCaseTest {

    private lateinit var repository: FakeCategoryRepository
    private lateinit var subCategoryItem: SubCategoryItem

    @Before
    fun setUp() {
        repository = FakeCategoryRepository()
        subCategoryItem = SubCategoryItem(
            subCategoryId = "123",
            height = 10,
            width = 10,
            url = "https://test"
        )
    }

    @Test
    fun `check id is null or not empty`(){
        assertThat(
            subCategoryItem.subCategoryId
        ).isNotEmpty()
    }

    @Test
    fun `check width is not null`(){
        assertThat(
            subCategoryItem.width
        ).isNotNull()
    }

    @Test
    fun `check height is not null`(){
        assertThat(
            subCategoryItem.height
        ).isNotNull()
    }

    @Test
    fun `check url is not empty`(){
        assertThat(
            subCategoryItem.url
        ).isNotEmpty()
    }

    @Test
    fun `check url is valid`() {
        assertThat(
            subCategoryItem.url.contains("http://") ||
                    subCategoryItem.url.contains("https://")
        ).isTrue()
    }

    @Test
    fun `check with is more than zero`() {
        assertThat(subCategoryItem.width).isGreaterThan(0)
    }

    @Test
    fun `check height is more than zero`() {
        assertThat(subCategoryItem.height).isGreaterThan(0)
    }

    @Test
    fun `check insert subCategory item`() = runBlocking {
        repository.insertSubCategoryItem(subCategoryItem)
        val items = repository.observeAllSubCategoryItems()
        assertThat(items.first()).contains(subCategoryItem)
    }
}