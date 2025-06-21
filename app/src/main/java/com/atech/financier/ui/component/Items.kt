package com.atech.financier.ui.component

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
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.unit.dp
import com.atech.financier.R

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
                    .background(MaterialTheme.colorScheme.secondaryContainer),
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
fun SearchBarItem(
    value: String = "",
    onValueChange: (String) -> Unit = {}
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surfaceContainerHigh)
            .fillMaxWidth()
            .defaultMinSize(minHeight = 56.dp),
        placeholder = {
            Text(
                text = "Найти статью"
            )
        },
        trailingIcon = {
            Icon(
                painter = painterResource(R.drawable.search),
                contentDescription = null
            )
        },
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}
