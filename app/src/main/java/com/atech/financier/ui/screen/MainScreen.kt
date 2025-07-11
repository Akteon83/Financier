package com.atech.financier.ui.screen

import android.annotation.SuppressLint
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.atech.financier.R
import com.atech.financier.ui.navigation.BottomNavigationItem
import com.atech.financier.ui.navigation.Screen
import com.atech.financier.ui.navigation.screen
import com.atech.financier.ui.navigation.selectedBottomItem
import com.atech.financier.ui.viewmodel.MainViewModel
import kotlin.enums.enumEntries

@SuppressLint("RestrictedApi")
@Composable
fun MainScreen(
    viewModel: MainViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
) {
    val navBackStack by navController.currentBackStack.collectAsState()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    MainScreenContent(
        screen = navBackStackEntry?.screen ?: Screen.Expenses,
        selectedBottomItem = navBackStack.selectedBottomItem,
        onActionClick = { screen ->
            when (screen) {
                is Screen.Expenses,
                is Screen.Revenues -> {
                    navController.navigate(
                        Screen.History(isIncome = screen is Screen.Revenues)
                    )
                }

                is Screen.Account -> {
                    navController.navigate(
                        Screen.AccountEditor
                    )
                }

                is Screen.AccountEditor -> {
                    viewModel.updateAccount()
                    navController.navigateUp()
                }

                is Screen.TransactionEditor -> {
                    viewModel.updateTransaction()
                    navController.navigateUp()
                }

                else -> {}
            }
        },
        onNavigationClick = { screen ->
            when (screen) {
                is Screen.History,
                is Screen.AccountEditor,
                is Screen.TransactionEditor -> navController.navigateUp()

                else -> {}
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
        },
        onFabClick = { screen ->
            when (screen) {
                Screen.Expenses,
                Screen.Revenues -> {
                    navController.navigate(
                        Screen.TransactionEditor(
                            isIncome = screen is Screen.Revenues,
                            id = -1
                        )
                    )
                }

                else -> {}
            }

        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding),
            startDestination = Screen.Expenses
        ) {

            composable<Screen.Expenses> {
                ExpensesScreen(navController = navController)
            }

            composable<Screen.Revenues> {
                RevenuesScreen(navController = navController)
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

            composable<Screen.History> {
                val args = it.toRoute<Screen.History>()
                HistoryScreen(isRevenuesHistory = args.isIncome)
            }

            composable<Screen.AccountEditor> {
                AccountEditorScreen()
            }

            composable<Screen.TransactionEditor> {
                val args = it.toRoute<Screen.TransactionEditor>()
                TransactionEditorScreen(
                    navController = navController,
                    isIncome = args.isIncome,
                    transactionId = args.id
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainScreenContent(
    screen: Screen,
    selectedBottomItem: BottomNavigationItem,
    onActionClick: (Screen) -> Unit = {},
    onNavigationClick: (Screen) -> Unit = {},
    onNavBarClick: (Screen) -> Unit = {},
    onFabClick: (Screen) -> Unit = {},
    content: @Composable ((PaddingValues) -> Unit) = {}
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(screen.screenTitle),
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    AnimatedVisibility(
                        screen is Screen.History
                                || screen is Screen.AccountEditor
                                || screen is Screen.TransactionEditor
                    ) {
                        IconButton(
                            onClick = { onNavigationClick(screen) }
                        ) {
                            Icon(
                                painter = painterResource(screen.navigationIcon),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                },
                actions = {
                    AnimatedVisibility(
                        screen !is Screen.Categories
                                && screen !is Screen.Settings
                    ) {
                        IconButton(
                            onClick = { onActionClick(screen) }
                        ) {
                            Icon(
                                painter = painterResource(screen.actionIcon),
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
                enumEntries<BottomNavigationItem>().forEach { item ->
                    NavigationBarItem(
                        selected = item == selectedBottomItem,
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
            AnimatedVisibility(screen is Screen.Expenses || screen is Screen.Revenues) {
                FloatingActionButton(
                    onClick = { onFabClick(screen) },
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
        Screen.Account, Screen.AccountEditor -> R.string.account_title
        Screen.Categories -> R.string.categories_title
        Screen.Settings -> R.string.settings
        is Screen.History -> R.string.history_title
        is Screen.TransactionEditor -> {
            if (this.isIncome) R.string.revenues_title else R.string.expenses_title
        }
    }

val Screen.actionIcon
    get() = when (this) {
        Screen.Expenses, Screen.Revenues -> R.drawable.mdi_history
        Screen.Account -> R.drawable.edit
        is Screen.History -> R.drawable.mdi_clipboard
        Screen.AccountEditor, is Screen.TransactionEditor -> R.drawable.check
        else -> R.drawable.empty
    }

val Screen.navigationIcon
    get() = when (this) {
        is Screen.History -> R.drawable.arrow_back
        Screen.AccountEditor, is Screen.TransactionEditor -> R.drawable.close
        else -> R.drawable.empty
    }
