package com.atech.financier.ui.screen

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Switch
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.atech.financier.DataStoreManager
import com.atech.financier.R
import com.atech.financier.ui.component.ColumnItem
import com.atech.financier.ui.component.LanguageSelectorDialog
import com.atech.financier.ui.theme.ColorMode
import com.atech.financier.ui.theme.FinancierTheme
import com.atech.financier.ui.util.LocaleManager
import com.atech.financier.ui.viewmodel.SettingsAction
import com.atech.financier.ui.viewmodel.SettingsState
import com.atech.financier.ui.viewmodel.SettingsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    SettingsScreenContent(
        state = state,
        onAction = viewModel::onAction,
        sheetState = sheetState,
        scope = scope
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingsScreenContent(
    state: SettingsState = SettingsState(),
    onAction: (SettingsAction) -> Unit = {},
    sheetState: SheetState = rememberModalBottomSheetState(),
    scope: CoroutineScope = rememberCoroutineScope()
) {
    val settingsItems = listOf(
        R.string.main_color,
        R.string.sounds,
        R.string.haptics,
        R.string.password,
        R.string.synchronization,
        R.string.language,
        R.string.about_app
    )

    val context = LocalContext.current

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
                        checked = state.darkThemeChecked,
                        onCheckedChange = {
                            onAction(SettingsAction.SetDarkTheme(it))
                        }
                    )
                }
            )
        }
        items(settingsItems) { item ->
            ColumnItem(
                title = stringResource(item),
                iconRight = {
                    Icon(
                        painter = painterResource(R.drawable.arrow_right),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            ) {
                when (item) {
                    R.string.main_color -> onAction(SettingsAction.ShowBottomSheet)
                    R.string.language -> onAction(SettingsAction.ShowLanguageDialog)
                }
            }
        }
    }

    if (state.showLanguageDialog) {
        LanguageSelectorDialog(
            onConfirm = { onAction(SettingsAction.SetLanguage(it, context)) },
            onDismiss = { onAction(SettingsAction.HideLanguageDialog) }
        )
    }

    if (state.showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { onAction(SettingsAction.HideBottomSheet) },
            sheetState = sheetState
        ) {

            ColumnItem(
                title = stringResource(R.string.purple),
                iconRight = {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .background(ColorMode.PURPLE.primary, CircleShape)
                    )
                }
            ) {
                onAction(SettingsAction.SetColorMode(ColorMode.PURPLE))
                scope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        onAction(SettingsAction.HideBottomSheet)
                    }
                }
            }

            ColumnItem(
                title = stringResource(R.string.green),
                iconRight = {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .background(ColorMode.GREEN.primary, CircleShape)
                    )
                }
            ) {
                onAction(SettingsAction.SetColorMode(ColorMode.GREEN))
                scope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        onAction(SettingsAction.HideBottomSheet)
                    }
                }
            }

            ColumnItem(
                title = stringResource(R.string.blue),
                iconRight = {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .background(ColorMode.BLUE.primary, CircleShape)
                    )
                }
            ) {
                onAction(SettingsAction.SetColorMode(ColorMode.BLUE))
                scope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        onAction(SettingsAction.HideBottomSheet)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun SettingsScreenPreview() {
    FinancierTheme {
        SettingsScreenContent()
    }
}
