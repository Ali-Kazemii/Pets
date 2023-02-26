package com.artinsoft.pets.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [CategoryItem::class, SubCategoryItem::class],
    version = 1,
    exportSchema = false
)
abstract class PetDatabase : RoomDatabase() {

    abstract fun categoryDao(): CategoryDao
    abstract fun subCategoryDao(): SubCategoryDao
}