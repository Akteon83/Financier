package com.atech.financier

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.atech.financier.ui.screens.AccountScreen
import com.atech.financier.ui.screens.ExpensesScreen
import com.atech.financier.ui.screens.RevenuesScreen
import com.atech.financier.ui.screens.SettingsScreen
import com.atech.financier.ui.theme.FinancierTheme
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FinancierTheme {

                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        CenterAlignedTopAppBar(
                            title = {
                                Text(
                                    text = stringResource(
                                        when {
                                            currentDestination?.hasRoute(ExpensesScreen::class) == true -> R.string.expenses_title
                                            currentDestination?.hasRoute(RevenuesScreen::class) == true -> R.string.revenues_title
                                            currentDestination?.hasRoute(AccountScreen::class) == true -> R.string.account_title
                                            currentDestination?.hasRoute(ItemsScreen::class) == true -> R.string.items_title
                                            currentDestination?.hasRoute(SettingsScreen::class) == true -> R.string.settings
                                            else -> {R.string.app_name}
                                        }
                                    ),
                                    style = MaterialTheme.typography.titleLarge
                                )
                            },
                            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                titleContentColor = MaterialTheme.colorScheme.onSurface
                            )
                        )
                    },
                    bottomBar = {
                        NavigationBar {
                            navigationBarItems.forEach { item ->
                                NavigationBarItem(
                                    selected = currentDestination?.hierarchy?.any {
                                        it.hasRoute(item.route::class)
                                    } == true,
                                    onClick = {
                                        navController.navigate(item.route) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    },
                                    icon = {
                                        Icon(
                                            painter = painterResource(item.icon),
                                            contentDescription = null
                                        )
                                    },
                                    label = {
                                        Text(
                                            text = stringResource(item.label),
                                            style = MaterialTheme.typography.labelSmall
                                        )
                                    }
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding),
                        startDestination = ExpensesScreen
                    ) {
                        composable<ExpensesScreen> {
                            ExpensesScreen()
                        }
                        composable<RevenuesScreen> {
                            RevenuesScreen()
                        }
                        composable<AccountScreen> {
                            AccountScreen()
                        }
                        composable<ItemsScreen> {
                            ExpensesScreen()
                        }
                        composable<SettingsScreen> {
                            SettingsScreen()
                        }
                    }
                }
            }
        }
    }
}

val navigationBarItems = listOf(
    BottomNavigationItem(
        R.string.expenses,
        R.drawable.expenses,
        ExpensesScreen
    ),
    BottomNavigationItem(
        R.string.revenues,
        R.drawable.revenues,
        RevenuesScreen
    ),
    BottomNavigationItem(
        R.string.account,
        R.drawable.account,
        AccountScreen
    ),
    BottomNavigationItem(
        R.string.items,
        R.drawable.items,
        ItemsScreen
    ),
    BottomNavigationItem(
        R.string.settings,
        R.drawable.settings,
        SettingsScreen
    ),
)

data class BottomNavigationItem<T : Any>(
    val label: Int,
    val icon: Int,
    val route: T
)

@Serializable
object ExpensesScreen

@Serializable
object RevenuesScreen

@Serializable
object AccountScreen

@Serializable
object ItemsScreen

@Serializable
object SettingsScreen