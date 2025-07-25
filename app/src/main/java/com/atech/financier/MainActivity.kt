package com.atech.financier

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.DisposableEffect
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.atech.financier.ui.screen.MainScreen
import com.atech.financier.ui.util.LocaleManager
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        setContent {

            DisposableEffect(Unit) {
                val job = kotlinx.coroutines.GlobalScope.launch {
                    DataStoreManager.language
                        .onEach { language ->
                            LocaleManager.setLocale(FinancierApplication.context,
                                language)
                        }
                        .collect()
                }

                onDispose {
                    job.cancel()
                }
            }

            MainScreen()
        }
    }

    override fun attachBaseContext(newBase: Context) {
        val lang = runBlocking { DataStoreManager.language.first() }
        super.attachBaseContext(LocaleManager.setLocale(newBase, lang))
    }
}
