package com.atech.financier.ui.mapper

import com.atech.financier.domain.model.Category
import com.atech.financier.ui.viewmodel.CategoryItemState

fun Category.toCategoryItemState(): CategoryItemState {
    return CategoryItemState(
        id = id,
        title = title,
        emoji = emoji,
    )
}
