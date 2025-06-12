package com.atech.financier.ui.components

import com.atech.financier.R
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.atech.financier.ui.theme.FinancierTheme

@Preview(showBackground = true)
@Composable
fun TopColumnItemPreview() {
    FinancierTheme {
        ColumnItem(
            modifier = Modifier.background(MaterialTheme.colorScheme.secondary),
            highEmphasis = true,
            title = "Всего",
            value = "600 000 $",
            description = "Джек",
            iconRight = {
                Icon(
                    painter = painterResource(R.drawable.chevron_right),
                    contentDescription = null
                )
            }
        )
    }
}

@Composable
fun ColumnItem(
    modifier: Modifier = Modifier,
    highEmphasis: Boolean = false,
    title: String = "",
    value: String = "",
    description: String = "",
    iconLeft: (@Composable () -> Unit)? = null,
    iconRight: (@Composable () -> Unit)? = null,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = (if (highEmphasis) 68 else 56).dp)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        iconLeft?.invoke()
        Column(
            modifier = Modifier.weight(1F)
        ) {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyLarge
            )
            if (description.isNotBlank()) {
                Text(
                    text = description,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        if (value.isNotBlank()) {
            Text(
                text = value,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyLarge
            )
        }
        iconRight?.invoke()
    }
}