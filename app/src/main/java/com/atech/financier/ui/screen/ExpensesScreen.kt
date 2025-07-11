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
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.atech.financier.R
import com.atech.financier.ui.component.ChevronIcon
import com.atech.financier.ui.component.ColumnItem
import com.atech.financier.ui.navigation.Screen
import com.atech.financier.ui.theme.FinancierTheme
import com.atech.financier.ui.theme.Trans
import com.atech.financier.ui.viewmodel.ExpensesState
import com.atech.financier.ui.viewmodel.ExpensesViewModel

@Composable
fun ExpensesScreen(
    viewModel: ExpensesViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    LaunchedEffect(Unit) { viewModel.loadTransactions() }
    val state by viewModel.state.collectAsStateWithLifecycle()
    ExpensesScreenContent(
        state = state,
        onItemClick = { id ->
            navController.navigate(
                Screen.TransactionEditor(
                    isIncome = false,
                    id = id
                )
            )
        },
        onRefresh = viewModel::updateTransactions
    )
}

@Composable
private fun ExpensesScreenContent(
    state: ExpensesState = ExpensesState(),
    onItemClick: (Int) -> Unit = {},
    onRefresh: () -> Unit = {}
) {
    Column(modifier = Modifier.background(MaterialTheme.colorScheme.outlineVariant)) {

        ColumnItem(
            title = stringResource(R.string.total),
            value = "${state.total} ${state.currency}",
            color = MaterialTheme.colorScheme.secondary,
            onClick = onRefresh
        )

        LazyColumn(
            contentPadding = PaddingValues(vertical = 1.dp),
            verticalArrangement = Arrangement.spacedBy(1.dp)
        ) {
            items(
                items = state.expenses,
                key = { expense -> expense.id }
            ) { expense ->
                ColumnItem(
                    title = expense.title,
                    value = "${expense.amount} ${state.currency}",
                    description = expense.description,
                    emoji = expense.emoji,
                    highEmphasis = true,
                    iconRight = { ChevronIcon() }
                ) { onItemClick(expense.id) }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ExpensesScreenPreview() {
    FinancierTheme {
        ExpensesScreenContent()
    }
}
