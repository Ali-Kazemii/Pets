package com.artinsoft.pets.presentation.category

import androidx.lifecycle.viewModelScope
import com.artinsoft.pets.data.local.CategoryItem
import com.artinsoft.pets.data.local.SubCategoryItem
import com.artinsoft.pets.domain.model.CategoryModel
import com.artinsoft.pets.domain.model.SubCategoryModel
import com.artinsoft.pets.domain.model.SubCategoryRequest
import com.artinsoft.pets.domain.usecase.CategoryUseCases
import com.artinsoft.pets.utils.BaseViewModel
import com.artinsoft.pets.utils.NetworkResponse
import com.artinsoft.pets.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val useCases: CategoryUseCases
) : BaseViewModel() {

    companion object {
        const val DEFAULT_PAGE_NUMBER = 1
    }

    val title = SingleLiveEvent<String?>()
    private var _listSubCategory = MutableStateFlow(listOf<SubCategoryModel?>())
    val listSubCategory: StateFlow<List<SubCategoryModel?>> = _listSubCategory

    var listCategory: ArrayList<CategoryModel?>? = null
    private val listItems: ArrayList<SubCategoryModel?> = arrayListOf()

    private var currentCategory: Int? = null
    private var isLoadMore: Boolean = false
    private var pageNumber: Int = 1

    init {
        viewModelScope.launch {
            showLoading()
            useCases.getCategoryFromApiUseCase.run(Unit).collect { category ->
                when (category) {
                    is NetworkResponse.Success -> {
                        listCategory = category.data
                        callSubCategory(listCategory?.get(0)?.id, DEFAULT_PAGE_NUMBER)
                        title.postValue(listCategory?.get(0)?.name)
                    }
                    is NetworkResponse.Failure -> {
                        failure.postValue(category.e.message)
                    }
                }
                hideLoading()
            }
        }
    }


    private fun setCurrentCategory(categoryId: Int?) {
        currentCategory = categoryId
    }

    fun loadMore() {
        isLoadMore = true
        pageNumber += 1
        callSubCategory(
            categoryId = currentCategory,
            page = pageNumber
        )
    }

    fun getSubCategory(
        categoryId: Int?
    ) {
        isLoadMore = false
        setCurrentCategory(categoryId)
        pageNumber = DEFAULT_PAGE_NUMBER
        callSubCategory(
            categoryId = categoryId,
            page = pageNumber
        )
    }

    private fun callSubCategory(
        categoryId: Int?,
        page: Int
    ) {
        showLoading()
        viewModelScope.launch {
            useCases.getSubCategoryFromApiUseCase.run(
                SubCategoryRequest(
                    categoryId = categoryId,
                    page = page
                )
            ).collect {
                when (it) {
                    is NetworkResponse.Success -> {
                        if (isLoadMore) {
                            listItems.addAll(it.data)
                            _listSubCategory.value = listItems.map {item -> item}
                        } else {
                            listItems.clear()
                            listItems.addAll(it.data)
                            _listSubCategory.value = it.data
                        }
                    }
                    is NetworkResponse.Failure -> {
                        failure.postValue(it.e.message)
                    }
                }
                hideLoading()
            }
        }
    }

    private fun insertCategoryItemIntoDb(categoryItem: CategoryModel) = viewModelScope.launch {
        useCases.insertCategoryItemUseCase.run(categoryItem)
    }

    private fun insertSubCategoryItemIntoDb(subCategoryModel: SubCategoryModel) =
        viewModelScope.launch {
            useCases.insertSubCategoryItemUseCase.run(subCategoryModel)
        }

    fun insertCategoryItem(id: Int?, name: String?) {
        val categoryItem = CategoryModel(id, name)
        insertCategoryItemIntoDb(categoryItem)
    }

    fun insertSubCategoryItem(id: String?, url: String?, height: Int?, width: Int?) {
        val subCategoryItem = SubCategoryModel(id = id, url = url, width = width, height = height)
        insertSubCategoryItemIntoDb(subCategoryItem)
    }

    fun deleteCategoryFromDb(categoryItem: CategoryItem) = viewModelScope.launch {
        useCases.deleteCategoryUseCase.run(categoryItem)
    }

    fun deleteSubCategoryFromDb(subCategoryItem: SubCategoryItem) = viewModelScope.launch {
        useCases.deleteSubCategoryUseCase.run(subCategoryItem)
    }
}