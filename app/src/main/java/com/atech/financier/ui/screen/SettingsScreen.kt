package com.atech.financier.ui.screen

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.atech.financier.R
import com.atech.financier.ui.component.ColumnItem
import com.atech.financier.ui.theme.FinancierTheme
import com.atech.financier.ui.viewmodel.SettingsViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = viewModel()
) {
    SettingsScreenContent(
        darkThemeChecked = viewModel.darkThemeChecked,
        onDarkThemeCheckedChange = viewModel::onDarkThemeCheckedChange
    )
}

@Composable
private fun SettingsScreenContent(
    darkThemeChecked: Boolean = false,
    onDarkThemeCheckedChange: (Boolean) -> Unit = {},
) {
    val settingsItems = listOf(
        stringResource(R.string.main_color),
        stringResource(R.string.sounds),
        stringResource(R.string.haptics),
        stringResource(R.string.password),
        stringResource(R.string.synchronization),
        stringResource(R.string.language),
        stringResource(R.string.about_app)
    )
    LazyColumn(
        modifier = Modifier.background(MaterialTheme.colorScheme.outlineVariant),
        contentPadding = PaddingValues(bottom = 1.dp),
        verticalArrangement = Arrangement.spacedBy(1.dp)
    ) {
        item {
            ColumnItem(
                title = stringResource(R.string.dark_theme),
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

@Preview(showBackground = true)
@Composable
private fun SettingsScreenPreview() {
    FinancierTheme {
        SettingsScreenContent()
    }
}
