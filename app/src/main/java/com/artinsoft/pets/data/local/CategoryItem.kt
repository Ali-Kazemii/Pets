package com.artinsoft.pets.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_items")
data class CategoryItem(
    @PrimaryKey(autoGenerate = true)
    var id: Int?= null,
    var name: String,
)
