package com.atech.financier.data.repository

import com.atech.financier.data.RetrofitInstance
import com.atech.financier.data.mapper.toDomain
import com.atech.financier.domain.model.Category
import com.atech.financier.domain.repository.CategoryRepository

object CategoryRepositoryImpl : CategoryRepository {

    private var _categories: List<Category>? = null

    override suspend fun getCategories(
        requireUpdate: Boolean,
    ): List<Category> {
        if (requireUpdate || _categories == null) loadCategories()
        return _categories ?: emptyList()
    }

    suspend fun loadCategories() {
        try {
            _categories = RetrofitInstance.api.getCategories()
                .body()?.map { it.toDomain() } ?: emptyList()
        } catch (e: Exception) {

        }
    }
}