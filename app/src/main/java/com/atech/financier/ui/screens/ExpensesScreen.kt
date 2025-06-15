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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.atech.financier.R
import com.atech.financier.domain.models.TransactionResponse
import com.atech.financier.ui.components.ColumnItem
import com.atech.financier.ui.theme.FinancierTheme
import com.atech.financier.ui.theme.Trans

@Preview(showBackground = true)
@Composable
private fun ExpensesScreenPreview() {
    FinancierTheme {
        ExpensesScreen()
    }
}

@Composable
fun ExpensesScreen(
    expenses: List<TransactionResponse> = emptyList<TransactionResponse>()
) {
    Column(modifier = Modifier.background(MaterialTheme.colorScheme.outlineVariant),) {
        ColumnItem(
            title = stringResource(R.string.total),
            value = "800 000 ₽",
            color = MaterialTheme.colorScheme.secondary
        )
        LazyColumn(
            contentPadding = PaddingValues(vertical = 1.dp),
            verticalArrangement = Arrangement.spacedBy(1.dp)
        ) {
            items(
                items = expenses,
                key = { expense -> expense.id }
            ) { expense ->
                ColumnItem(
                    title = expense.category.name,
                    value = expense.amount + " " + Currency.getInstance(expense.account.currency).symbol,
                    description = expense.comment ?: "",
                    emoji = expense.category.emoji,
                    highEmphasis = true,
                    iconRight = {
                        Icon(
                            painter = painterResource(R.drawable.chevron_right),
                            contentDescription = null,
                            tint = Trans
                        )
                    }
                )
            }
        }
    }
}