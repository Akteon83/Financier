package com.atech.financier.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.atech.financier.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageSelectorDialog(
    onConfirm: (String) -> Unit = {},
    onDismiss: () -> Unit = {},
) {
    BasicAlertDialog(
        onDismissRequest = onDismiss
    ) {
        Column {

            ColumnItem(title = stringResource(R.string.by_default)) {
                onConfirm("default")
                onDismiss()
            }

            ColumnItem(title = stringResource(R.string.eng)) {
                onConfirm("en")
                onDismiss()
            }

            ColumnItem(title = stringResource(R.string.rus)) {
                onConfirm("ru")
                onDismiss()
            }
        }
    }
}
