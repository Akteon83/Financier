package com.atech.financier.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {

    var darkThemeChecked by mutableStateOf(false)

    fun onDarkThemeCheckedChange(checked: Boolean) {
        darkThemeChecked = checked
    }
}
