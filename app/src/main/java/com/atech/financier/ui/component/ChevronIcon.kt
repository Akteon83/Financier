package com.atech.financier.ui.component

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.atech.financier.R
import com.atech.financier.ui.theme.Trans

@Composable
fun ChevronIcon() {
    Icon(
        painter = painterResource(R.drawable.chevron_right),
        contentDescription = null,
        tint = Trans
    )
}
