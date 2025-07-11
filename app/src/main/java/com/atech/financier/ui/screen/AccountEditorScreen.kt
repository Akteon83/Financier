package com.atech.financier.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.atech.financier.R
import com.atech.financier.ui.component.ChevronIcon
import com.atech.financier.ui.component.ColumnItem
import com.atech.financier.ui.component.TextFieldItem
import com.atech.financier.ui.theme.FinancierTheme
import com.atech.financier.ui.viewmodel.AccountEditorState
import com.atech.financier.ui.viewmodel.AccountEditorViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountEditorScreen(
    viewModel: AccountEditorViewModel = viewModel(),
) {
    LaunchedEffect(Unit) { viewModel.loadAccount() }
    val state by viewModel.state.collectAsStateWithLifecycle()
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    AccountEditorScreenContent(
        state = state,
        onNameChange = viewModel::onNameChange,
        onBalanceChange = viewModel::onBalanceChange,
        onCurrencyChange = viewModel::onCurrencyChange,
        showBottomSheet = viewModel::showBottomSheet,
        hideBottomSheet = viewModel::hideBottomSheet,
        sheetState = sheetState,
        scope = scope
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AccountEditorScreenContent(
    state: AccountEditorState = AccountEditorState(),
    onNameChange: (String) -> Unit = {},
    onBalanceChange: (String) -> Unit = {},
    onCurrencyChange: (String) -> Unit = {},
    showBottomSheet: () -> Unit = {},
    hideBottomSheet: () -> Unit = {},
    sheetState: SheetState = rememberModalBottomSheetState(),
    scope: CoroutineScope = rememberCoroutineScope()
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.outlineVariant)
            .padding(bottom = 1.dp),
        verticalArrangement = Arrangement.spacedBy(1.dp)
    ) {

        TextFieldItem(
            title = stringResource(R.string.name),
            value = state.name,
            onValueChange = onNameChange,
        )

        TextFieldItem(
            title = stringResource(R.string.balance),
            value = state.balance,
            onValueChange = onBalanceChange,
            keyboardType = KeyboardType.Number
        )

        ColumnItem(
            title = stringResource(R.string.currency),
            value = state.currency,
            iconRight = { ChevronIcon() },
            onClick = showBottomSheet
        )
    }

    if (state.showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = hideBottomSheet,
            sheetState = sheetState
        ) {

            ColumnItem(
                title = stringResource(R.string.rub),
                emoji = "₽"
            ) {
                onCurrencyChange("₽")
                scope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) { hideBottomSheet() }
                }
            }

            ColumnItem(
                title = stringResource(R.string.usd),
                emoji = "$"
            ) {
                onCurrencyChange("$")
                scope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) { hideBottomSheet() }
                }
            }

            ColumnItem(
                title = stringResource(R.string.eur),
                emoji = "€"
            ) {
                onCurrencyChange("€")
                scope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) { hideBottomSheet() }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun AccountEditorScreenPreview() {
    FinancierTheme {
        AccountEditorScreenContent()
    }
}
