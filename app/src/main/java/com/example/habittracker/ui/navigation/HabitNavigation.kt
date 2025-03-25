package com.example.habittracker.ui.navigation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.habittracker.ui.screens.home.HabitTrackerScreen
import com.example.habittracker.ui.screens.home.HomeDestination
import com.example.habittracker.ui.screens.info.InfoDestination
import com.example.habittracker.ui.screens.info.InfoScreen
import com.example.habittracker.ui.screens.item.create.CreateHabitScreen
import com.example.habittracker.ui.screens.item.create.HabitEntryDestination
import com.example.habittracker.ui.screens.item.edit.EditHabitDestination
import com.example.habittracker.ui.screens.item.edit.EditHabitScreen

@Composable
fun HabitNavigation(
    navController: NavHostController,
    onClickOpenDrawer: () -> Unit
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
                onClickOpenDrawer = onClickOpenDrawer,
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
            CreateHabitScreen(
                navigateBack = { navController.navigateUp() },
                modifier = Modifier.fillMaxWidth()
            )
        }

        composable(route = InfoDestination.route) {
            InfoScreen(
                onClickOpenDrawer = onClickOpenDrawer,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
