package com.artinsoft.pets.domain.usecase

import com.artinsoft.pets.data.local.FakeCategoryRepository
import com.artinsoft.pets.utils.NetworkResponse
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetSubCategoryFromApiUseCaseTest {

    private lateinit var repository: FakeCategoryRepository

    @Before
    fun setUp() {
        repository = FakeCategoryRepository()
    }

    @Test
    fun `check getSubCategory, return Failure`() = runBlocking{
        repository.setShouldReturnNetworkError(true)
        val items = repository.getSubCategory(1,1)
        assertThat(items.first() is NetworkResponse.Failure).isTrue()
    }

    @Test
    fun `check getSubCategory, return Success`() = runBlocking{
        repository.setShouldReturnNetworkError(false)
        val items = repository.getSubCategory(1, 1)
        assertThat(items.first() is NetworkResponse.Success).isTrue()
    }
}