package com.atech.financier.ui.screens

import android.icu.util.Currency
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.atech.financier.R
import com.atech.financier.domain.models.Category
import com.atech.financier.ui.components.ColumnItem
import com.atech.financier.ui.components.SearchColumnItem
import com.atech.financier.ui.theme.FinancierTheme
import com.atech.financier.ui.theme.Trans

@Preview(showBackground = true)
@Composable
private fun ItemsScreenPreview() {
    FinancierTheme {
        ItemsScreen()
    }
}

@Composable
fun ItemsScreen(
    items: List<Category> = emptyList<Category>()
) {
    Column(modifier = Modifier.background(MaterialTheme.colorScheme.outlineVariant)) {
        SearchColumnItem()
        LazyColumn(
            contentPadding = PaddingValues(1.dp),
            verticalArrangement = Arrangement.spacedBy(1.dp)
        ) {
            items(
                items = items,
                key = { item -> item.id }
            ) { item ->
                ColumnItem(
                    title = item.name,
                    highEmphasis = true,
                    emoji = item.emoji
                )
            }
        }
    }
}