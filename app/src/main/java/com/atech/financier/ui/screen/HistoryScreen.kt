package com.atech.financier.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.atech.financier.R
import com.atech.financier.ui.component.ChevronIcon
import com.atech.financier.ui.component.ColumnItem
import com.atech.financier.ui.theme.FinancierTheme
import com.atech.financier.ui.theme.Trans
import com.atech.financier.ui.viewmodel.HistoryState
import com.atech.financier.ui.viewmodel.HistoryViewModel

@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel = viewModel(),
    isRevenuesHistory : Boolean = false
) {
    viewModel.revenuesHistory(isRevenuesHistory)
    LaunchedEffect(Unit) { viewModel.loadTransactions() }
    val state by viewModel.state.collectAsStateWithLifecycle()
    HistoryScreenContent(
        state = state,
        onRefresh = viewModel::updateTransactions
    )
}

@Composable
private fun HistoryScreenContent(
    state: HistoryState = HistoryState(),
    onRefresh: () -> Unit = {}
) {
    Column(
        modifier = Modifier.background(MaterialTheme.colorScheme.outlineVariant),
        verticalArrangement = Arrangement.spacedBy(1.dp)
    ) {

        ColumnItem(
            title = stringResource(R.string.beginning),
            value = state.beginning,
            color = MaterialTheme.colorScheme.secondary
        )

        ColumnItem(
            title = stringResource(R.string.ending),
            value = state.ending,
            color = MaterialTheme.colorScheme.secondary
        )

        ColumnItem(
            title = stringResource(R.string.amount),
            value = "${state.total} ${state.currency}",
            color = MaterialTheme.colorScheme.secondary,
            onClick = onRefresh
        )

        LazyColumn(
            contentPadding = PaddingValues(bottom = 1.dp),
            verticalArrangement = Arrangement.spacedBy(1.dp)
        ) {
            items(
                items = state.transactions
            ) { transaction ->
                ColumnItem(
                    title = transaction.title,
                    value = "${transaction.amount} ${state.currency}\n${transaction.dateTime}",
                    description = transaction.description,
                    emoji = transaction.emoji,
                    highEmphasis = true,
                    iconRight = { ChevronIcon() }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ExpensesScreenPreview() {
    FinancierTheme {
        HistoryScreenContent()
    }
}
