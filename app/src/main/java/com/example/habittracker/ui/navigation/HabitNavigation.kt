package com.example.habittracker.ui.navigation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.habittracker.ui.screens.home.HabitTrackerScreen
import com.example.habittracker.ui.screens.home.HomeDestination
import com.example.habittracker.ui.screens.home.components.DrawerContent
import com.example.habittracker.ui.screens.info.InfoDestination
import com.example.habittracker.ui.screens.info.InfoScreen
import com.example.habittracker.ui.screens.item.EditHabitDestination
import com.example.habittracker.ui.screens.item.EditHabitScreen
import com.example.habittracker.ui.screens.item.HabitEntryDestination
import com.example.habittracker.ui.screens.item.HabitEntryScreen
import kotlinx.coroutines.launch

@Composable
fun HabitNavigation(
    navController: NavHostController
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                DrawerContent(
                    onDrawerClick = { navController.navigate(it.route) },
                    onClickCloseDrawer = {
                        scope.launch {
                            drawerState.close()
                        }
                    }
                )
            }
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = HomeDestination.route
        ) {
            composable(route = HomeDestination.route) {
                HabitTrackerScreen(
                    onClickAddItem = {
                        navController.navigate(HabitEntryDestination.route)
                    },
                    onClickHabit = {
                        navController.navigate("${EditHabitDestination.route}/$it")
                    },
                    onClickEdit = {
                        navController.navigate("${EditHabitDestination.route}/$it")
                    },
                    onClickOpenDrawer = {
                        scope.launch { drawerState.open() }
                    },
                    modifier = Modifier
                )
            }

            composable(
                route = EditHabitDestination.routeWithArgs,
                arguments = listOf(navArgument(EditHabitDestination.itemIdArg) {
                    type = NavType.StringType
                })
            ) {
                EditHabitScreen(
                    navigateBack = { navController.navigateUp() },
                    modifier = Modifier
                )
            }

            composable(route = HabitEntryDestination.route) {
                HabitEntryScreen(
                    navigateBack = { navController.navigateUp() },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            composable(route = InfoDestination.route) {
                InfoScreen(
                    onClickOpenDrawer = {
                        scope.launch { drawerState.open() }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}