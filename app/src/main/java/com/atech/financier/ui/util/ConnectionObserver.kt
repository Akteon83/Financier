package com.atech.financier.ui.util

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.core.content.getSystemService
import com.atech.financier.FinancierApplication

object ConnectionObserver {

    private val manager = FinancierApplication.Companion.context
        .getSystemService<ConnectivityManager>()!!

    fun hasInternetAccess(): Boolean {
        val network = manager.activeNetwork ?: return false
        val capabilities = manager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    }
}
