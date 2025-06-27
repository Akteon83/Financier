package com.atech.financier

import android.content.pm.ActivityInfo
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.atech.financier.ui.navigation.BottomNavigationItem.Companion.navigationBarItems
import com.atech.financier.ui.navigation.Screen
import com.atech.financier.ui.navigation.screen
import com.atech.financier.ui.screen.AccountScreen
import com.atech.financier.ui.screen.ExpensesScreen
import com.atech.financier.ui.screen.CategoriesScreen
import com.atech.financier.ui.screen.HistoryScreen
import com.atech.financier.ui.screen.MainScreen
import com.atech.financier.ui.screen.RevenuesScreen
import com.atech.financier.ui.screen.SettingsScreen
import com.atech.financier.ui.theme.FinancierTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        setContent {
            FinancierTheme {

                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()

                MainScreen(
                    currentScreen = navBackStackEntry?.destination?.screen ?: Screen.Expenses,
                    navController = navController
                )

                /*Scaffold(
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
                                            is Screen.Categories -> R.string.categories_title
                                            is Screen.Settings -> R.string.settings
                                            is Screen.ExpensesHistory,
                                            is Screen.RevenuesHistory -> R.string.history_title
                                        }
                                    ),
                                    style = MaterialTheme.typography.titleLarge
                                )
                            },
                            navigationIcon = {},
                            actions = {
                                if (currentScreen !is Screen.Categories && currentScreen !is Screen.Settings) {
                                    IconButton(
                                        onClick = {
                                            when (currentScreen) {
                                                is Screen.Expenses, is Screen.Revenues -> {
                                                    navController.navigate(
                                                        if (currentScreen is Screen.Expenses)
                                                            Screen.ExpensesHistory
                                                        else Screen.RevenuesHistory
                                                    ) {
                                                        popUpTo(navController.graph.findStartDestination().id) {
                                                            saveState = true
                                                        }
                                                        launchSingleTop = true
                                                        restoreState = true
                                                    }
                                                }

                                                else -> {}
                                            }
                                        }
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
                                    } == true
                                            || (item.route is Screen.Expenses
                                            && currentScreen is Screen.ExpensesHistory)
                                            || (item.route is Screen.Revenues
                                            && currentScreen is Screen.RevenuesHistory),
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
                                elevation = FloatingActionButtonDefaults.elevation(
                                    0.dp,
                                    0.dp,
                                    0.dp,
                                    0.dp
                                )
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
                            ExpensesScreen()
                        }

                        composable<Screen.Revenues> {
                            RevenuesScreen()
                        }

                        composable<Screen.Account> {
                            AccountScreen()
                        }

                        composable<Screen.Categories> {
                            CategoriesScreen()
                        }

                        composable<Screen.Settings> {
                            SettingsScreen()
                        }

                        composable<Screen.ExpensesHistory> {
                            HistoryScreen()
                        }

                        composable<Screen.RevenuesHistory> {
                            HistoryScreen()
                        }

                    }
                }*/
            }
        }
    }
}
