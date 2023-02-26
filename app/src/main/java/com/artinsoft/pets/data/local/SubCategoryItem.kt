package com.artinsoft.pets.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sub_category_items")
data class SubCategoryItem(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var subCategoryId: String?= null,
    var height: Int,
    var width: Int,
    var url: String,
)