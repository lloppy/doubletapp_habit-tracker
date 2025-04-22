package com.example.habittracker.ui.navigation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.habittracker.ui.screens.home.HabitTrackerScreen
import com.example.habittracker.ui.screens.home.HabitTrackerViewModel
import com.example.habittracker.ui.screens.home.HomeDestination
import com.example.habittracker.ui.screens.info.InfoDestination
import com.example.habittracker.ui.screens.info.InfoScreen
import com.example.habittracker.ui.screens.item.create.CreateHabitScreen
import com.example.habittracker.ui.screens.item.create.CreateHabitViewModel
import com.example.habittracker.ui.screens.item.create.HabitEntryDestination
import com.example.habittracker.ui.screens.item.edit.EditHabitDestination
import com.example.habittracker.ui.screens.item.edit.EditHabitScreen
import com.example.habittracker.ui.screens.item.edit.EditHabitViewModel
import com.example.habittracker.ui.screens.language.LanguageDestination
import com.example.habittracker.ui.screens.language.LanguageScreen
import com.example.habittracker.ui.screens.language.LanguageScreenViewModel
import com.example.habittracker.ui.screens.sync.SyncDestination
import com.example.habittracker.ui.screens.sync.SyncScreen
import com.example.habittracker.ui.screens.sync.SyncScreenViewModel

@Composable
fun HabitNavigation(
    navController: NavHostController,
    viewModelFactory: ViewModelProvider.Factory,
    onClickOpenDrawer: () -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route
    ) {
        composable(route = HomeDestination.route) {
            val viewModel: HabitTrackerViewModel = viewModel(factory = viewModelFactory)
            HabitTrackerScreen(
                viewModel = viewModel,
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
        ) { backStackEntry ->
            val stringId = backStackEntry.arguments?.getString(EditHabitDestination.itemIdArg)

            val viewModel: EditHabitViewModel = viewModel(factory = viewModelFactory)
            EditHabitScreen(
                stringId = stringId,
                viewModel = viewModel,
                navigateBack = { navController.navigateUp() },
                modifier = Modifier
            )
        }

        composable(route = HabitEntryDestination.route) {
            val viewModel: CreateHabitViewModel = viewModel(factory = viewModelFactory)
            CreateHabitScreen(
                viewModel = viewModel,
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

        composable(route = LanguageDestination.route) {
            val viewModel: LanguageScreenViewModel = viewModel(factory = viewModelFactory)
            LanguageScreen(
                viewModel = viewModel,
                onClickOpenDrawer = onClickOpenDrawer,
                modifier = Modifier.fillMaxWidth()
            )
        }

        composable(route = SyncDestination.route) {
            val viewModel: SyncScreenViewModel = viewModel(factory = viewModelFactory)
            SyncScreen(
                viewModel = viewModel,
                onClickOpenDrawer = onClickOpenDrawer,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
