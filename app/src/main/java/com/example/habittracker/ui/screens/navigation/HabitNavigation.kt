package com.example.habittracker.ui.screens.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.habittracker.ui.screens.home.HabitTrackerScreen
import com.example.habittracker.ui.screens.home.HomeDestination
import com.example.habittracker.ui.screens.item.HabitDetailDestination
import com.example.habittracker.ui.screens.item.HabitDetailScreen
import com.example.habittracker.ui.screens.item.HabitEditDestination
import com.example.habittracker.ui.screens.item.HabitEditScreen
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
                    navController.navigate("${HabitDetailDestination.route}/$it")
                },
                onClickEdit = {
                    navController.navigate("${HabitEditDestination.route}/$it")
                },
                onClickDelete = {

                },
                modifier = Modifier
            )
        }

        composable(
            route = HabitDetailDestination.routeWithArgs,
            arguments = listOf(navArgument(HabitDetailDestination.itemIdArg) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val habitName = backStackEntry.arguments?.getString(HabitDetailDestination.itemIdArg)
                ?: error("${HabitDetailDestination.itemIdArg} cannot be null")

            HabitDetailScreen(
                habitName = habitName,
                onClickEdit = {
                    navController.navigate("${HabitEditDestination.route}/$habitName")
                },
                navigateBack = { navController.navigateUp() },
                modifier = Modifier
            )
        }

        composable(
            route = HabitEditDestination.routeWithArgs,
            arguments = listOf(navArgument(HabitEditDestination.itemIdArg) {
                type = NavType.StringType
            })
        ) {
            HabitEditScreen(
                navigateBack = { navController.navigateUp() },
                modifier = Modifier
            )
        }

        composable(
            route = HabitEntryDestination.routeWithArgs,
            arguments = listOf(navArgument(HabitEntryDestination.itemIdArg) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val habitName = backStackEntry.arguments?.getString(HabitEntryDestination.itemIdArg)
                ?: error("${HabitEntryDestination.itemIdArg} cannot be null")

            HabitEntryScreen(
                habitName = habitName,
                navigateBack = { navController.navigateUp() },
                modifier = Modifier
            )
        }
    }
}