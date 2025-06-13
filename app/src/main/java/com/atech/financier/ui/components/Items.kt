package com.atech.financier.ui.components

import com.atech.financier.R
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.atech.financier.ui.theme.FinancierTheme

@Preview(showBackground = true)
@Composable
fun TopColumnItemPreview() {
    FinancierTheme {
        ColumnItem(
            title = "Всего",
            value = "600 000 $",
            description = "Джек",
            highEmphasis = true,
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
    title: String = "",
    value: String = "",
    description: String = "",
    emoji: String? = null,
    color: Color = MaterialTheme.colorScheme.surface,
    highEmphasis: Boolean = false,
    iconRight: (@Composable () -> Unit)? = null,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .background(color)
            .fillMaxWidth()
            .defaultMinSize(minHeight = (if (highEmphasis) 68 else 56).dp)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        emoji?.let {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(color = MaterialTheme.colorScheme.secondaryContainer),
                contentAlignment = Alignment.Center
            ) {
                BasicText(
                    text = emoji,
                    style = TextStyle(
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    autoSize = TextAutoSize.StepBased(
                        minFontSize = with(LocalDensity.current) { 8.dp.toSp() },
                        maxFontSize = with(LocalDensity.current) { 16.dp.toSp() },
                        stepSize = with(LocalDensity.current) { 8.dp.toSp() }
                    )
                )
            }
        }
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

@Composable
fun SearchColumnItem() {
    Row(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surfaceContainerHigh)
            .fillMaxWidth()
            .defaultMinSize(minHeight = 56.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Найти статью",
            modifier = Modifier.weight(1F),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyLarge
        )
        Icon(
            painter = painterResource(R.drawable.search),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}