package com.atech.financier.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.atech.financier.ui.component.ColumnItem
import com.atech.financier.ui.component.SearchBarItem
import com.atech.financier.ui.theme.FinancierTheme
import com.atech.financier.ui.viewmodel.CategoriesState
import com.atech.financier.ui.viewmodel.CategoriesViewModel

@Composable
fun CategoriesScreen(
    viewModel: CategoriesViewModel = viewModel()
) {
    LaunchedEffect(Unit) { viewModel.loadCategories() }
    val state by viewModel.state.collectAsStateWithLifecycle()
    CategoriesScreenContent(
        state = state,
        onSearchChange = viewModel::onSearchChange
    )
}

@Composable
private fun CategoriesScreenContent(
    state: CategoriesState = CategoriesState(),
    onSearchChange: (String) -> Unit = {}
) {
    Column(modifier = Modifier.background(MaterialTheme.colorScheme.outlineVariant)) {

        SearchBarItem(
            value = state.search,
            onValueChange = onSearchChange
        )

        LazyColumn(
            contentPadding = PaddingValues(1.dp),
            verticalArrangement = Arrangement.spacedBy(1.dp)
        ) {
            items(
                items = state.categories.filter { it.title.contains(state.search, true) }
            ) { category ->
                ColumnItem(
                    title = category.title,
                    highEmphasis = true,
                    emoji = category.emoji
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ItemsScreenPreview() {
    FinancierTheme {
        CategoriesScreenContent()
    }
}
