package com.atech.financier.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.atech.financier.R
import com.atech.financier.ui.navigation.BottomNavigationItem.Companion.navigationBarItems
import com.atech.financier.ui.navigation.Screen
import com.atech.financier.ui.viewmodel.MainViewModel

@Composable
fun MainScreen(
    viewModel: MainViewModel = viewModel(),
    currentScreen: Screen,
    navController: NavHostController = rememberNavController(),
) {
    MainScreenContent(
        currentScreen = currentScreen,
        onActionClick = {
            when (currentScreen) {
                is Screen.Expenses, is Screen.Revenues -> {
                    navController.navigate(
                        when (currentScreen) {
                            is Screen.Expenses -> Screen.ExpensesHistory
                            else -> Screen.RevenuesHistory
                        }
                    ) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }

                is Screen.Account -> {
                    navController.navigate(
                        Screen.AccountEditor
                    ) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }

                is Screen.AccountEditor -> {
                    viewModel.updateAccount()
                    navController.navigate(
                        Screen.Account
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
        },
        onNavigationClick = {
            navController.navigate(
                when (currentScreen) {
                    is Screen.ExpensesHistory -> Screen.Expenses
                    is Screen.RevenuesHistory -> Screen.Revenues
                    is Screen.AccountEditor -> Screen.Account
                    else -> Screen.Expenses
                }
            ) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        },
        onNavBarClick = { route ->
            navController.navigate(route) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }
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

            composable<Screen.AccountEditor> {
                AccountEditor()
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainScreenContent(
    currentScreen: Screen,
    onActionClick: () -> Unit = {},
    onNavigationClick: () -> Unit = {},
    onNavBarClick: (Screen) -> Unit = {},
    content: @Composable ((PaddingValues) -> Unit) = {}
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(currentScreen.screenTitle),
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    if (currentScreen is Screen.ExpensesHistory
                        || currentScreen is Screen.RevenuesHistory
                        || currentScreen is Screen.AccountEditor
                    ) {
                        IconButton(
                            onClick = onNavigationClick
                        ) {
                            Icon(
                                painter = painterResource(currentScreen.navigationIcon),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                },
                actions = {
                    if (currentScreen !is Screen.Categories && currentScreen !is Screen.Settings) {
                        IconButton(
                            onClick = onActionClick
                        ) {
                            Icon(
                                painter = painterResource(currentScreen.actionIcon),
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
                        selected = currentScreen == item.route
                                || (item.route == Screen.Expenses
                                && currentScreen == Screen.ExpensesHistory)
                                || (item.route == Screen.Revenues
                                && currentScreen == Screen.RevenuesHistory)
                                || (item.route == Screen.Account
                                && currentScreen == Screen.AccountEditor),
                        onClick = { onNavBarClick(item.route) },
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
            AnimatedVisibility(currentScreen is Screen.Expenses || currentScreen is Screen.Revenues) {
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
        content(innerPadding)
    }
}

val Screen.screenTitle
    get() = when (this) {
        Screen.Expenses -> R.string.expenses_title
        Screen.Revenues -> R.string.revenues_title
        Screen.Account,
        Screen.AccountEditor -> R.string.account_title

        Screen.Categories -> R.string.categories_title
        Screen.Settings -> R.string.settings
        Screen.ExpensesHistory,
        Screen.RevenuesHistory -> R.string.history_title
    }

val Screen.actionIcon
    get() = when (this) {
        is Screen.Expenses, is Screen.Revenues -> R.drawable.mdi_history
        is Screen.Account -> R.drawable.edit
        is Screen.ExpensesHistory, is Screen.RevenuesHistory -> R.drawable.mdi_clipboard
        is Screen.AccountEditor -> R.drawable.check
        else -> R.drawable.empty
    }

val Screen.navigationIcon
    get() = when (this) {
        is Screen.ExpensesHistory, is Screen.RevenuesHistory -> R.drawable.arrow_back
        is Screen.AccountEditor -> R.drawable.close
        else -> R.drawable.empty
    }
