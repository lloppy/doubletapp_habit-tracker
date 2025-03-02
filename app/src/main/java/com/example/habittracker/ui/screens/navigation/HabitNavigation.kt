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
import com.example.habittracker.ui.screens.item.AddHabitDestination
import com.example.habittracker.ui.screens.item.AddHabitScreen
import com.example.habittracker.ui.screens.item.EditHabitScreen
import com.example.habittracker.ui.screens.item.HabitDetailDestination
import com.example.habittracker.ui.screens.item.HabitDetailScreen
import com.example.habittracker.ui.screens.item.HabitEditDestination

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
                    navController.navigate(AddHabitDestination.route)
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
            EditHabitScreen(
                navigateBack = { navController.navigateUp() },
                modifier = Modifier
            )
        }

        composable(
            route = AddHabitDestination.route,
        ) {
            AddHabitScreen(
                navigateBack = { navController.navigateUp() },
                modifier = Modifier
            )
        }
    }
}