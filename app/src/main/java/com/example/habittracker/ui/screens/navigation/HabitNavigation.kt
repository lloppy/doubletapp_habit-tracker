package com.example.habittracker.ui.screens.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.habittracker.ui.screens.HabitTrackerViewModel
import com.example.habittracker.ui.screens.home.HabitTrackerScreen
import com.example.habittracker.ui.screens.home.HomeDestination
import com.example.habittracker.ui.screens.item.HabitDetailDestination
import com.example.habittracker.ui.screens.item.HabitDetailScreen
import com.example.habittracker.ui.screens.item.HabitEntryDestination
import com.example.habittracker.ui.screens.item.HabitEntryScreen

@Composable
fun HabitNavigation(
    navController: NavHostController
) {
    val viewModel: HabitTrackerViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = HomeDestination.route
    ) {
        composable(route = HomeDestination.route) {
            HabitTrackerScreen(
                uiState = viewModel.uiState,
                onClickAddItem = {
                    navController.navigate(HabitEntryDestination.route)
                },
                onClickHabit = { habitName ->
                    navController.navigate(HabitDetailDestination.route + "/$habitName")
                },
                modifier = Modifier
            )
        }

        val detailsArgument = "detailRoute"
        composable(
            route = HabitDetailDestination.route + "/{$detailsArgument}",
            arguments = listOf(navArgument(detailsArgument) { type = NavType.StringType })
        ) { backStackEntry ->
            val habitName = backStackEntry.arguments?.getString(detailsArgument)
                ?: error("$detailsArgument cannot be null")

            HabitDetailScreen(
                habitName = habitName,
                habit = viewModel.getDetailsFor(habitName),
                navigateBack = { navController.navigateUp() },
                modifier = Modifier
            )
        }

        composable(route = HabitEntryDestination.route) {
            HabitEntryScreen(

            )
        }
    }
}