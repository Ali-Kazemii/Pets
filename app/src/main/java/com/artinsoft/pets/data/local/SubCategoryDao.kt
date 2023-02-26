package com.artinsoft.pets.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SubCategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubCategoryItem(subCategoryItem: SubCategoryItem)

    @Delete
    suspend fun deleteSubCategoryItem(subCategoryItem: SubCategoryItem)

    @Query("SELECT * FROM sub_category_items")
    fun observeAllSubCategoryItems(): Flow<List<SubCategoryItem>>
}