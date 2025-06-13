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
private fun RevenuesScreenPreview() {
    FinancierTheme {
        RevenuesScreen()
    }
}

@Composable
fun RevenuesScreen(
    revenues: List<TransactionResponse> = emptyList<TransactionResponse>()
) {
    Column(modifier = Modifier.background(MaterialTheme.colorScheme.outlineVariant)) {
        ColumnItem(
            title = stringResource(R.string.total),
            value = "600 000 ₽",
            color = MaterialTheme.colorScheme.secondary
        )
        LazyColumn(
            contentPadding = PaddingValues(vertical = 1.dp),
            verticalArrangement = Arrangement.spacedBy(1.dp)
        ) {
            items(
                items = revenues,
                key = { revenue -> revenue.id }
            ) { revenue ->
                ColumnItem(
                    title = revenue.category.name,
                    value = revenue.amount + " " + Currency.getInstance(revenue.account.currency).symbol,
                    description = revenue.comment ?: "",
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