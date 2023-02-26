package com.artinsoft.pets.domain.usecase

import com.artinsoft.pets.data.local.FakeCategoryRepository
import com.artinsoft.pets.utils.NetworkResponse
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetCategoryFromApiUseCaseTest {

    private lateinit var repository: FakeCategoryRepository

    @Before
    fun setUp() {
        repository = FakeCategoryRepository()
    }

    @Test
    fun `check get categories from api, return Failure`() = runBlocking{
        repository.setShouldReturnNetworkError(true)
        val items= repository.getCategory()
        assertThat(items.first() is NetworkResponse.Failure).isTrue()
    }

    @Test
    fun `check get categories from api, return Success`() = runBlocking{
        repository.setShouldReturnNetworkError(false)
        val items= repository.getCategory()
        assertThat(items.first() is NetworkResponse.Success).isTrue()
    }
}