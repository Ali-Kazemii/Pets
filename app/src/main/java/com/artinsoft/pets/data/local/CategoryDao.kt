package com.artinsoft.pets.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategoryItem(categoryItem: CategoryItem)

    @Delete
    suspend fun deleteCategoryItem(categoryItem: CategoryItem)

    @Query("SELECT * FROM category_items")
    fun observeAllCategoryItems(): Flow<List<CategoryItem>>
}