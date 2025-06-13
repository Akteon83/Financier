package com.atech.financier

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.FloatingActionButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeFloatingActionButton
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
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.atech.financier.ui.screens.AccountScreen
import com.atech.financier.ui.screens.ExpensesScreen
import com.atech.financier.ui.screens.ItemsScreen
import com.atech.financier.ui.screens.RevenuesScreen
import com.atech.financier.ui.screens.SettingsScreen
import com.atech.financier.ui.theme.FinancierTheme
import com.atech.financier.ui.viewmodels.ExpensesViewModel
import com.atech.financier.ui.viewmodels.ItemsViewModel
import com.atech.financier.ui.viewmodels.RevenuesViewModel
import com.atech.financier.ui.viewmodels.SettingsViewModel
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        val expensesViewModel = ExpensesViewModel()
        val revenuesViewModel = RevenuesViewModel()
        val itemsViewModel = ItemsViewModel()
        val settingsViewModel = SettingsViewModel()
        setContent {
            FinancierTheme {

                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                val currentScreen = when {
                    currentDestination?.hasRoute(Screen.Expenses::class) == true -> Screen.Expenses
                    currentDestination?.hasRoute(Screen.Revenues::class) == true -> Screen.Revenues
                    currentDestination?.hasRoute(Screen.Account::class) == true -> Screen.Account
                    currentDestination?.hasRoute(Screen.Items::class) == true -> Screen.Items
                    currentDestination?.hasRoute(Screen.Settings::class) == true -> Screen.Settings
                    else -> {
                        Screen.Expenses
                    }
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        CenterAlignedTopAppBar(
                            title = {
                                Text(
                                    text = stringResource(
                                        when (currentScreen) {
                                            is Screen.Expenses -> R.string.expenses_title
                                            is Screen.Revenues -> R.string.revenues_title
                                            is Screen.Account -> R.string.account_title
                                            is Screen.Items -> R.string.items_title
                                            is Screen.Settings -> R.string.settings
                                        }
                                    ),
                                    style = MaterialTheme.typography.titleLarge
                                )
                            },
                            navigationIcon = {},
                            actions = {
                                if (currentScreen !is Screen.Items && currentScreen !is Screen.Settings) {
                                    IconButton(
                                        onClick = {}
                                    ) {
                                        Icon(
                                            painter = painterResource(
                                                when (currentScreen) {
                                                    is Screen.Expenses, is Screen.Revenues -> R.drawable.mdi_history
                                                    is Screen.Account -> R.drawable.edit
                                                    else -> R.drawable.chevron_right
                                                }
                                            ),
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
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
                    },
                    floatingActionButton = {
                        if (currentScreen is Screen.Expenses || currentScreen is Screen.Revenues) {
                            FloatingActionButton(
                                onClick = {},
                                shape = CircleShape,
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.surface,
                                elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp)
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.add),
                                    contentDescription = null
                                )
                            }
                        }
                    },
                    containerColor = MaterialTheme.colorScheme.surface
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding),
                        startDestination = Screen.Expenses
                    ) {
                        composable<Screen.Expenses> {
                            ExpensesScreen(
                                expensesViewModel.expenses
                            )
                        }
                        composable<Screen.Revenues> {
                            RevenuesScreen(
                                revenuesViewModel.revenues
                            )
                        }
                        composable<Screen.Account> {
                            AccountScreen()
                        }
                        composable<Screen.Items> {
                            ItemsScreen(
                                itemsViewModel.items
                            )
                        }
                        composable<Screen.Settings> {
                            SettingsScreen(
                                settingsViewModel.darkThemeChecked.value,
                                settingsViewModel::onDarkThemeCheckedChange
                            )
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
        Screen.Expenses
    ),
    BottomNavigationItem(
        R.string.revenues,
        R.drawable.revenues,
        Screen.Revenues
    ),
    BottomNavigationItem(
        R.string.account,
        R.drawable.account,
        Screen.Account
    ),
    BottomNavigationItem(
        R.string.items,
        R.drawable.items,
        Screen.Items
    ),
    BottomNavigationItem(
        R.string.settings,
        R.drawable.settings,
        Screen.Settings
    ),
)

data class BottomNavigationItem<T : Screen>(
    val label: Int,
    val icon: Int,
    val route: T
)

sealed interface Screen {
    @Serializable
    object Expenses : Screen

    @Serializable
    object Revenues : Screen

    @Serializable
    object Account : Screen

    @Serializable
    object Items : Screen

    @Serializable
    object Settings : Screen
}