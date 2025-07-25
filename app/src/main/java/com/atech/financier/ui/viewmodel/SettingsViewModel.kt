package com.atech.financier.ui.viewmodel

import android.app.Activity
import android.content.Context
import androidx.compose.runtime.Immutable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atech.financier.DataStoreManager
import com.atech.financier.ui.theme.ColorMode
import com.atech.financier.ui.util.LocaleManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel : ViewModel() {

    private val _state = MutableStateFlow(SettingsState())
    val state: StateFlow<SettingsState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update { currentState ->
                currentState.copy(
                    darkThemeChecked = DataStoreManager.isDarkTheme()
                )
            }
        }
    }

    fun onAction(action: SettingsAction) {
        when (action) {
            is SettingsAction.SetDarkTheme -> onDarkThemeCheckedChange(action.checked)
            is SettingsAction.SetColorMode -> onColorModeChange(action.colorMode)
            is SettingsAction.SetLanguage -> onLanguageChange(action.language, action.context)
            SettingsAction.ShowBottomSheet -> showBottomSheet()
            SettingsAction.HideBottomSheet -> hideBottomSheet()
            SettingsAction.ShowLanguageDialog -> showLanguageDialog()
            SettingsAction.HideLanguageDialog -> hideLanguageDialog()
        }
    }

    private fun onDarkThemeCheckedChange(checked: Boolean) {
        _state.update { currentState ->
            currentState.copy(
                darkThemeChecked = checked
            )
        }
        viewModelScope.launch {
            DataStoreManager.setDarkTheme(checked)
        }
    }

    private fun onColorModeChange(colorMode: ColorMode) {
        viewModelScope.launch {
            DataStoreManager.setColorMode(colorMode)
        }
    }

    private fun onLanguageChange(language: String, context: Context) {
        viewModelScope.launch {
            DataStoreManager.setLanguage(language)
            (context as Activity).recreate()
        }
    }

    private fun showBottomSheet() {
        _state.update { currentState ->
            currentState.copy(
                showBottomSheet = true
            )
        }
    }

    private fun hideBottomSheet() {
        _state.update { currentState ->
            currentState.copy(
                showBottomSheet = false
            )
        }
    }

    private fun showLanguageDialog() {
        _state.update { currentState ->
            currentState.copy(
                showLanguageDialog = true
            )
        }
    }

    private fun hideLanguageDialog() {
        _state.update { currentState ->
            currentState.copy(
                showLanguageDialog = false
            )
        }
    }
}

@Immutable
data class SettingsState(
    val darkThemeChecked: Boolean = false,
    val showBottomSheet: Boolean = false,
    val showLanguageDialog: Boolean = false,
)

sealed interface SettingsAction {
    data class SetDarkTheme(val checked: Boolean) : SettingsAction
    data class SetColorMode(val colorMode: ColorMode) : SettingsAction
    data class SetLanguage(val language: String, val context: Context) : SettingsAction
    object ShowBottomSheet : SettingsAction
    object HideBottomSheet : SettingsAction
    object ShowLanguageDialog : SettingsAction
    object HideLanguageDialog : SettingsAction
}
