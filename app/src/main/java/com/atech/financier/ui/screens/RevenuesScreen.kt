package com.atech.financier.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.atech.financier.ui.components.ColumnItem
import com.atech.financier.ui.theme.FinancierTheme

@Preview(showBackground = true)
@Composable
private fun RevenuesScreenPreview() {
    FinancierTheme {
        RevenuesScreen()
    }
}

@Composable
fun RevenuesScreen() {
    LazyColumn(
        modifier = Modifier.background(MaterialTheme.colorScheme.outlineVariant),
        verticalArrangement = Arrangement.spacedBy(1.dp)
    ) {
        item {
            ColumnItem(
                modifier = Modifier.background(MaterialTheme.colorScheme.secondary),
                title = "Всего",
                value = "-500 000 $"
            )
        }
    }
}