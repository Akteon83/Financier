package com.atech.financier.ui.util

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import java.util.*

object LocaleManager {

    fun setLocale(context: Context, languageCode: String): Context {

        val locale = Locale(if (languageCode == "default") Resources
            .getSystem().configuration.locales[0].language else languageCode)
        Locale.setDefault(locale)

        val resources = context.resources
        val config = Configuration(resources.configuration)

        config.setLocale(locale)
        config.setLayoutDirection(locale)

        return context.createConfigurationContext(config)
    }
}
