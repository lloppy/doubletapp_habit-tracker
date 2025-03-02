package com.example.habittracker.ui.screens.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.habittracker.R
import com.example.habittracker.ui.screens.HabitTrackerViewModel
import com.example.habittracker.ui.screens.home.HabitTrackerScreen
import com.example.habittracker.ui.screens.item.HabitDetailScreen

@Composable
fun HabitNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val appName = stringResource(R.string.app_name)
    var topBarTitle by remember { mutableStateOf(appName) }
    val viewModel: HabitTrackerViewModel = viewModel()
    val onBackHandler = {
        topBarTitle = appName
        navController.navigateUp()
    }

    NavHost(navController = navController, startDestination = "home", modifier = modifier) {
        composable(route = "home") {
            HabitTrackerScreen(
                uiState = viewModel.uiState,
                onClickHabit = { habitName ->
                    navController.navigate("details/$habitName")
                },
                modifier = Modifier
            )
        }
        val detailsArgument = "detailRoute"
        composable(
            route = "details/{$detailsArgument}",
            arguments = listOf(navArgument(detailsArgument) { type = NavType.StringType })
        ) { backStackEntry ->
            val habitName = backStackEntry.arguments?.getString(detailsArgument)
                ?: error("$detailsArgument cannot be null")
            Log.e("onClickHabit", habitName + "navigation2")

            HabitDetailScreen(
                habitName = habitName,
                habit = viewModel.getDetailsFor(habitName),
                navigateBack = { navController.navigateUp() },
                modifier = Modifier
            )
        }
    }
}