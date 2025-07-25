package com.atech.financier

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

class FinancierApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    val dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    companion object {

        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
            private set
    }
}
