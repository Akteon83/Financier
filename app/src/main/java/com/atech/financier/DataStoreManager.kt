package com.atech.financier

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.atech.financier.FinancierApplication.Companion.context
import com.atech.financier.ui.theme.ColorMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.util.Locale

object DataStoreManager {

    suspend fun setDarkTheme(isDarkTheme: Boolean) {
        (context as FinancierApplication).dataStore.edit { preferences ->
            preferences[PreferencesKeys.IS_DARK_THEME] = isDarkTheme
        }
    }

    suspend fun setColorMode(colorMode: ColorMode) {
        (context as FinancierApplication).dataStore.edit { preferences ->
            preferences[PreferencesKeys.PRIMARY_COLOR] = when (colorMode) {
                ColorMode.PURPLE -> 1
                ColorMode.GREEN -> 2
                ColorMode.BLUE -> 3
            }
        }
    }

    suspend fun setLanguage(languageCode: String) {
        (context as FinancierApplication).dataStore.edit { preferences ->
            preferences[PreferencesKeys.LANGUAGE_CODE] = languageCode
        }
    }

    suspend fun isDarkTheme(): Boolean {
        return (context as FinancierApplication).dataStore.data
            .first()[PreferencesKeys.IS_DARK_THEME] == true
    }

    suspend fun getColorMode(): ColorMode {
        return when ((context as FinancierApplication).dataStore.data
            .first()[PreferencesKeys.PRIMARY_COLOR]) {
            2 -> ColorMode.GREEN
            3 -> ColorMode.BLUE
            else -> ColorMode.GREEN
        }
    }

    suspend fun getLanguage(): String {
        return (context as FinancierApplication).dataStore.data
            .first()[PreferencesKeys.LANGUAGE_CODE] ?: Locale.getDefault().language
    }

    val isDarkTheme: Flow<Boolean> = (context as FinancierApplication).dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.IS_DARK_THEME] ?: false
        }

    val colorMode: Flow<ColorMode> = (context as FinancierApplication).dataStore.data
        .map { preferences ->
            when (preferences[PreferencesKeys.PRIMARY_COLOR]) {
                1 -> ColorMode.PURPLE
                2 -> ColorMode.GREEN
                3 -> ColorMode.BLUE
                else -> ColorMode.GREEN
            }
        }

    val language: Flow<String> = (context as FinancierApplication).dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.LANGUAGE_CODE] ?: "default"
        }
}

object PreferencesKeys {

    val IS_DARK_THEME = booleanPreferencesKey("is_dark_theme")
    val PRIMARY_COLOR = intPreferencesKey("primary_color")
    val LANGUAGE_CODE = stringPreferencesKey("language_code")
}
