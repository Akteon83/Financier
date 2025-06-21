package com.atech.financier.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.atech.financier.R
import com.atech.financier.ui.component.ColumnItem
import com.atech.financier.ui.theme.FinancierTheme
import com.atech.financier.ui.theme.Trans

@Composable
fun AccountScreen() {
    Column(
        modifier = Modifier.background(MaterialTheme.colorScheme.outlineVariant),
        verticalArrangement = Arrangement.spacedBy(1.dp)
    ) {
        ColumnItem(
            title = stringResource(R.string.balance),
            value = "-600 000 $",
            color = MaterialTheme.colorScheme.secondary,
            iconRight = {
                Icon(
                    painter = painterResource(R.drawable.chevron_right),
                    contentDescription = null,
                    tint = Trans
                )
            }
        )
        ColumnItem(
            title = stringResource(R.string.currency),
            value = "$",
            color = MaterialTheme.colorScheme.secondary,
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

@Preview(showBackground = true)
@Composable
private fun AccountScreenPreview() {
    FinancierTheme {
        AccountScreen()
    }
}
