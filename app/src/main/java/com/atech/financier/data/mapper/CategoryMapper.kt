package com.atech.financier.data.mapper

import com.atech.financier.data.local.entity.CategoryEntity
import com.atech.financier.data.remote.dto.CategoryDto
import com.atech.financier.domain.model.Category

fun CategoryDto.toDomain() = Category(
    id = id,
    title = name,
    emoji = emoji,
    isIncome = isIncome,
)

fun CategoryEntity.toDomain() = Category(
    id = id,
    title = name,
    emoji = emoji,
    isIncome = isIncome,
)

fun CategoryDto.toEntity() = CategoryEntity(
    id = id,
    name = name,
    emoji = emoji,
    isIncome = isIncome,
)
