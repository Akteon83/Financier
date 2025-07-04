package com.atech.financier.domain.repository

import com.atech.financier.domain.model.Category

interface CategoryRepository {

    suspend fun getCategories(
        requireUpdate: Boolean = false,
    ): List<Category>
}