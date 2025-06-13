package com.atech.financier.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.atech.financier.R
import com.atech.financier.ui.components.ColumnItem
import com.atech.financier.ui.theme.FinancierTheme

@Preview(showBackground = true)
@Composable
private fun SettingsScreenPreview() {
    FinancierTheme {
        SettingsScreen()
    }
}

@Composable
fun SettingsScreen(
    darkThemeChecked: Boolean = false,
    onDarkThemeCheckedChange: (Boolean) -> Unit = {},
) {
    val settingsItems = listOf(
        "Основной цвет",
        "Звуки",
        "Хаптики",
        "Код пароль",
        "Cинхронизация",
        "Язык",
        "О программе"
    )
    LazyColumn(
        modifier = Modifier.background(MaterialTheme.colorScheme.outlineVariant),
        contentPadding = PaddingValues(bottom = 1.dp),
        verticalArrangement = Arrangement.spacedBy(1.dp)
    ) {
        item {
            ColumnItem(
                title = "Тёмная тема",
                iconRight = {
                    Switch(
                        checked = darkThemeChecked,
                        onCheckedChange = {
                            onDarkThemeCheckedChange(it)
                        }
                    )
                }
            )
        }
        items(settingsItems) { item ->
            ColumnItem(
                title = item,
                iconRight = {
                    Icon(
                        painter = painterResource(R.drawable.arrow_right),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            )
        }
    }
}