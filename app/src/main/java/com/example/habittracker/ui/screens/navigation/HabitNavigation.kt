package com.example.habittracker.ui.screens.navigation

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
import com.example.habittracker.ui.screens.item.EditHabitDestination
import com.example.habittracker.ui.screens.item.EditHabitScreen
import com.example.habittracker.ui.screens.item.HabitEntryDestination
import com.example.habittracker.ui.screens.item.HabitEntryScreen

@Composable
fun HabitNavigation(
    navController: NavHostController
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
                onClickDelete = {

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

        composable(
            route = HabitEntryDestination.route,
        ) {
            HabitEntryScreen(
                navigateBack = { navController.navigateUp() },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}