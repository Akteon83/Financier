package com.atech.financier.data.repository

import com.atech.financier.data.local.RoomInstance
import com.atech.financier.data.remote.RetrofitInstance
import com.atech.financier.data.mapper.toDomain
import com.atech.financier.data.mapper.toEntity
import com.atech.financier.data.remote.dto.CategoryDto
import com.atech.financier.domain.model.Category
import com.atech.financier.domain.repository.CategoryRepository
import com.atech.financier.ui.util.ConnectionObserver

object CategoryRepositoryImpl : CategoryRepository {

    private var _categories: List<Category>? = null

    override suspend fun getCategories(
        requireUpdate: Boolean,
    ): List<Category> {
        if (requireUpdate || _categories == null) {
            loadDatabase()
            loadCategories(requireSync = true)
        }
        return _categories ?: emptyList()
    }

    suspend fun loadCategories(
        requireSync: Boolean = false,
    ) {
        try {
            if (ConnectionObserver.hasInternetAccess()) {

                val response = RetrofitInstance.api.getCategories()
                    .body() ?: emptyList()

                _categories = response.map { it.toDomain() }
                if (requireSync) syncDatabase(response)
            }
        } catch (e: Exception) {

        }
    }
    private suspend fun loadDatabase() {
        _categories = RoomInstance.database.categoryDao.getAll().map { it.toDomain() }
    }

    private suspend fun syncDatabase(categories: List<CategoryDto>) {

        val localCategories = RoomInstance.database.categoryDao
            .getAll().map { it to false }.associateBy { it.first.id }.toMutableMap()

        categories.forEach {
            val localCategory = localCategories.getOrDefault(it.id, null)
            RoomInstance.database.categoryDao.upsert(it.toEntity())
            if (localCategory != null) {
                localCategories[it.id] = localCategory.first to true
            }
        }

        localCategories.forEach {
            if (!it.value.second) {
                RoomInstance.database.categoryDao.delete(it.value.first)
            }
        }
    }
}