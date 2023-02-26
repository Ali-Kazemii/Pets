package com.artinsoft.pets.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Categories(
    val list: ArrayList<CategoryModel?> = arrayListOf()
):Parcelable

@Parcelize
data class CategoryModel(
    val id: Int?= null,
    val name: String?= null
):Parcelable