package com.atech.financier.ui.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {

    val darkThemeChecked = mutableStateOf(false)

    fun onDarkThemeCheckedChange(checked: Boolean) {
        darkThemeChecked.value = checked
    }
}