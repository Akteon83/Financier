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
import com.atech.financier.ui.component.DateSelectorDialog
import com.atech.financier.ui.component.TextFieldItem
import com.atech.financier.ui.component.TimeSelectorDialog
import com.atech.financier.ui.theme.FinancierTheme
import com.atech.financier.ui.util.toFormattedDate
import com.atech.financier.ui.util.toFormattedTime
import com.atech.financier.ui.viewmodel.CategoryItemState
import com.atech.financier.ui.viewmodel.TransactionEditorAction
import com.atech.financier.ui.viewmodel.TransactionEditorState
import com.atech.financier.ui.viewmodel.TransactionEditorViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDate
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
        onAction = viewModel::onAction,
        onDeleteClick = {
            viewModel.deleteTransaction()
            navController.navigateUp()
        },
        sheetState = sheetState,
        scope = scope
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionEditorScreenContent(
    state: TransactionEditorState = TransactionEditorState(),
    onAction: (TransactionEditorAction) -> Unit = {},
    onDeleteClick: () -> Unit = {},
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
                onClick = { onAction(TransactionEditorAction.ShowBottomSheet) }
            )

            TextFieldItem(
                title = stringResource(R.string.amount),
                value = state.amount,
                onValueChange = { onAction(TransactionEditorAction.ChangeAmount(it)) },
                keyboardType = KeyboardType.Number
            )

            ColumnItem(
                title = stringResource(R.string.date),
                value = state.date.toFormattedDate(),
                highEmphasis = true,
                onClick = { onAction(TransactionEditorAction.ShowDatePicker) }
            )

            ColumnItem(
                title = stringResource(R.string.time),
                value = state.time.toFormattedTime(),
                highEmphasis = true,
                onClick = { onAction(TransactionEditorAction.ShowTimePicker) }
            )

            TextFieldItem(
                value = state.title,
                onValueChange = { onAction(TransactionEditorAction.ChangeTitle(it)) }
            )
        }

        TextButton(
            onClick = onDeleteClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
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
        DateSelectorDialog(
            onConfirm = { onAction(TransactionEditorAction.ChangeDate(it)) },
            onDismiss = { onAction(TransactionEditorAction.HideDatePicker) }
        )
    }

    if (state.showTimePicker) {
        TimeSelectorDialog(
            onConfirm = { onAction(TransactionEditorAction.ChangeTime(it)) },
            onDismiss = { onAction(TransactionEditorAction.HideTimePicker) }
        )
    }

    if (state.showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { onAction(TransactionEditorAction.HideBottomSheet) },
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
                        onAction(TransactionEditorAction.ChangeCategory(category))
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                onAction(TransactionEditorAction.HideBottomSheet)
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
