package com.atech.financier.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.atech.financier.ui.theme.FinancierTheme

@Composable
fun TextFieldItem(
    title: String = "",
    value: String = "",
    color: Color = MaterialTheme.colorScheme.surface,
    highEmphasis: Boolean = false,
    onValueChange: (String) -> Unit = {},
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Row(
        modifier = Modifier
            .background(color)
            .fillMaxWidth()
            .padding(start = (if (title.isBlank()) 0 else 16).dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        if (title.isNotBlank()) {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyLarge
            )
        }

        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .background(color)
                .weight(1F)
                .defaultMinSize(minHeight = (if (highEmphasis) 68 else 56).dp),
            textStyle = LocalTextStyle.current
                .copy(textAlign = if (title.isNotBlank()) TextAlign.End else TextAlign.Start),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TextFieldItemPreview() {
    FinancierTheme {
        TextFieldItem(
            title = "",
            value = "600 000"
        )
    }
}
