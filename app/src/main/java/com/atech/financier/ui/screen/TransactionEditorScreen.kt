package com.atech.financier.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.atech.financier.R
import com.atech.financier.ui.component.ChevronIcon
import com.atech.financier.ui.component.ColumnItem
import com.atech.financier.ui.component.DatePickerModal
import com.atech.financier.ui.component.TextFieldItem
import com.atech.financier.ui.component.TimePickerInput
import com.atech.financier.ui.theme.FinancierTheme
import com.atech.financier.ui.util.toFormattedDate
import com.atech.financier.ui.util.toFormattedTime
import com.atech.financier.ui.viewmodel.CategoryItemState
import com.atech.financier.ui.viewmodel.TransactionEditorState
import com.atech.financier.ui.viewmodel.TransactionEditorViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.nio.file.WatchEvent
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionEditorScreen(
    viewModel: TransactionEditorViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
    isIncome: Boolean = false,
    transactionId: Int = -1
) {
    LaunchedEffect(Unit) {
        viewModel.setTransactionInfo(isIncome = isIncome, id = transactionId)
        viewModel.loadCategories()
        if (transactionId != -1) viewModel.loadTransaction() else viewModel.loadAccount()
    }
    val state by viewModel.state.collectAsStateWithLifecycle()
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    TransactionEditorScreenContent(
        state = state,
        onAmountChange = viewModel::onAmountChange,
        onTitleChange = viewModel::onTitleChange,
        onCategoryChange = viewModel::onCategoryChange,
        onDateChange = viewModel::onDateChange,
        onTimeChange = viewModel::onTimeChange,
        onDeleteClick = {
            viewModel.deleteTransaction()
            navController.navigateUp()
        },
        showDatePicker = viewModel::showDatePicker,
        hideDatePicker = viewModel::hideDatePicker,
        showTimePicker = viewModel::showTimePicker,
        hideTimePicker = viewModel::hideTimePicker,
        showBottomSheet = viewModel::showBottomSheet,
        hideBottomSheet = viewModel::hideBottomSheet,
        sheetState = sheetState,
        scope = scope
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionEditorScreenContent(
    state: TransactionEditorState = TransactionEditorState(),
    onAmountChange: (String) -> Unit = {},
    onTitleChange: (String) -> Unit = {},
    onCategoryChange: (CategoryItemState) -> Unit = {},
    onDateChange: (Long?) -> Unit = {},
    onTimeChange: (LocalTime) -> Unit = {},
    onDeleteClick: () -> Unit = {},
    showDatePicker: () -> Unit = {},
    hideDatePicker: () -> Unit = {},
    showTimePicker: () -> Unit = {},
    hideTimePicker: () -> Unit = {},
    showBottomSheet: () -> Unit = {},
    hideBottomSheet: () -> Unit = {},
    sheetState: SheetState = rememberModalBottomSheetState(),
    scope: CoroutineScope = rememberCoroutineScope()
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.outlineVariant)
                .padding(bottom = 1.dp),
            verticalArrangement = Arrangement.spacedBy(1.dp)
        ) {

            ColumnItem(
                title = stringResource(R.string.account),
                value = state.accountName,
                highEmphasis = true,
                iconRight = { ChevronIcon() }
            )

            ColumnItem(
                title = stringResource(R.string.category),
                value = state.categoryTitle,
                highEmphasis = true,
                iconRight = { ChevronIcon() },
                onClick = showBottomSheet
            )

            TextFieldItem(
                title = stringResource(R.string.amount),
                value = state.amount,
                onValueChange = onAmountChange,
                keyboardType = KeyboardType.Number
            )

            ColumnItem(
                title = stringResource(R.string.date),
                value = state.date.toFormattedDate(),
                highEmphasis = true,
                onClick = showDatePicker
            )

            ColumnItem(
                title = stringResource(R.string.time),
                value = state.time.toFormattedTime(),
                highEmphasis = true,
                onClick = showTimePicker
            )

            TextFieldItem(
                value = state.title,
                onValueChange = onTitleChange
            )
        }

        TextButton(
            onClick = onDeleteClick,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            colors = ButtonDefaults.buttonColors().copy(
                containerColor = MaterialTheme.colorScheme.error,
                contentColor = MaterialTheme.colorScheme.onError
            )
        ) {
            Text(
                text = stringResource(R.string.delete_transaction),
                style = MaterialTheme.typography.labelLarge
            )
        }
    }

    if (state.showDatePicker) {
        DatePickerModal(
            onDateSelected = onDateChange,
            onDismiss = hideDatePicker
        )
    }

    if (state.showTimePicker) {
        TimePickerInput(
            onConfirm = { state ->
                onTimeChange(LocalTime.of(state.hour, state.minute))
                hideTimePicker()
            },
            onDismiss = hideTimePicker
        )
    }

    if (state.showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = hideBottomSheet,
            sheetState = sheetState
        ) {
            LazyColumn(
                contentPadding = PaddingValues(vertical = 1.dp),
                verticalArrangement = Arrangement.spacedBy(1.dp)
            ) {
                items(
                    items = state.categories,
                    key = { category -> category.id }
                ) { category ->
                    ColumnItem(
                        title = category.title,
                        emoji = category.emoji
                    ) {
                        onCategoryChange(category)
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                hideBottomSheet()
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun TransactionEditorPreview() {
    FinancierTheme {
        TransactionEditorScreenContent()
    }
}
