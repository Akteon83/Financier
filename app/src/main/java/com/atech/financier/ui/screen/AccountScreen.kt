package com.atech.financier.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import com.atech.financier.ui.viewmodel.AccountState
import com.atech.financier.ui.viewmodel.AccountViewModel

@Composable
fun AccountScreen(
    viewModel: AccountViewModel = viewModel()
) {
    LaunchedEffect(Unit) { viewModel.loadAccount() }
    val state by viewModel.state.collectAsStateWithLifecycle()
    AccountScreenContent(
        state = state
    )
}

@Composable
private fun AccountScreenContent(
    state: AccountState = AccountState()
) {
    Column(
        modifier = Modifier.background(MaterialTheme.colorScheme.outlineVariant),
        verticalArrangement = Arrangement.spacedBy(1.dp)
    ) {

        ColumnItem(
            title = stringResource(R.string.balance),
            value = "${state.balance} ${state.currency}",
            color = MaterialTheme.colorScheme.secondary,
            iconRight = { ChevronIcon() }
        )

        ColumnItem(
            title = stringResource(R.string.currency),
            value = state.currency,
            color = MaterialTheme.colorScheme.secondary,
            iconRight = { ChevronIcon() }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AccountScreenPreview() {
    FinancierTheme {
        AccountScreenContent()
    }
}
