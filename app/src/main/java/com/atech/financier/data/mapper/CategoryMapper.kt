package com.atech.financier.data.mapper

import com.atech.financier.data.dto.CategoryDto
import com.atech.financier.domain.model.Category

fun CategoryDto.toDomain() = Category(
    id = id,
    title = name,
    emoji = emoji,
    isIncome = isIncome,
)
