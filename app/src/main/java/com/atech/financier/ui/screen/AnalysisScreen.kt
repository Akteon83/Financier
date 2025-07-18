package com.atech.financier.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.atech.financier.R
import com.atech.financier.ui.component.ChevronIcon
import com.atech.financier.ui.component.ColumnItem
import com.atech.financier.ui.component.DateSelectorDialog
import com.atech.financier.ui.theme.FinancierTheme
import com.atech.financier.ui.util.toAmount
import com.atech.financier.ui.util.toFormattedDate
import com.atech.financier.ui.viewmodel.AnalysisAction
import com.atech.financier.ui.viewmodel.AnalysisState
import com.atech.financier.ui.viewmodel.AnalysisViewModel

@Composable
fun AnalysisScreen(
    viewModel: AnalysisViewModel = viewModel(),
    isRevenuesAnalysis: Boolean = false
) {
    viewModel.revenuesAnalysis(isRevenuesAnalysis)
    LaunchedEffect(Unit) { viewModel.loadAnalysis() }
    val state by viewModel.state.collectAsStateWithLifecycle()

    AnalysisScreenContent(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
private fun AnalysisScreenContent(
    state: AnalysisState = AnalysisState(),
    onAction: (AnalysisAction) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.outlineVariant)
            .padding(bottom = 1.dp),
        verticalArrangement = Arrangement.spacedBy(1.dp)
    ) {

        ColumnItem(
            title = stringResource(R.string.beginning),
            iconRight = {
                Text(
                    text = state.startDate.toFormattedDate(),
                    modifier = Modifier.background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = CircleShape
                    ).padding(vertical = 6.dp, horizontal = 16.dp),
                    style = MaterialTheme.typography.titleMedium
                )
            },
            onClick = { onAction(AnalysisAction.ShowStartPicker) }
        )

        ColumnItem(
            title = stringResource(R.string.ending),
            iconRight = {
                Text(
                    text = state.endDate.toFormattedDate(),
                    modifier = Modifier.background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = CircleShape
                    ).padding(vertical = 6.dp, horizontal = 16.dp),
                    style = MaterialTheme.typography.titleMedium
                )
            },
            onClick = { onAction(AnalysisAction.ShowEndPicker) }
        )

        ColumnItem(
            title = stringResource(R.string.amount),
            value = "${state.total} ${state.currency}"
        )

        LazyColumn(
            modifier = Modifier.background(MaterialTheme.colorScheme.outlineVariant),
            verticalArrangement = Arrangement.spacedBy(1.dp)
        ) {
            items(
                items = state.categories,
                key = { category -> category.id }
            ) { category ->
                ColumnItem(
                    title = category.title,
                    value = "${if (category.share != 0) category.share else "<1"}%\n" +
                            "${category.amount.toAmount()} ${state.currency}",
                    emoji = category.emoji,
                    highEmphasis = true,
                    iconRight = { ChevronIcon() }
                )
            }
        }

        if (state.showStartPicker) {
            DateSelectorDialog(
                onConfirm = { onAction(AnalysisAction.ChangeStartDate(it)) },
                onDismiss = { onAction(AnalysisAction.HideStartPicker) }
            )
        }

        if (state.showEndPicker) {
            DateSelectorDialog(
                onConfirm = { onAction(AnalysisAction.ChangeEndDate(it)) },
                onDismiss = { onAction(AnalysisAction.HideEndPicker) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AnalysisScreenPreview() {
    FinancierTheme {
        AnalysisScreenContent()
    }
}
