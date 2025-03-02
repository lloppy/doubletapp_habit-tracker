package com.example.habittracker.ui.screens.item

import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.habittracker.R
import com.example.habittracker.model.Habit
import com.example.habittracker.ui.AppViewModelProvider
import com.example.habittracker.ui.screens.home.HabitTrackerViewModel
import com.example.habittracker.ui.screens.navigation.NavigationDestination

object HabitEntryDestination : NavigationDestination {
    override val route = "entry_habit"
    override val title = R.string.entry
    const val itemIdArg = "itemId"
    val routeWithArgs = "$route/{$itemIdArg}"
}

@Composable
fun HabitEntryScreen(
    habitName: String,
    navigateBack: () -> Boolean,
    modifier: Modifier = Modifier,
    viewModel: HabitEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val selectedHabit = viewModel.entryUiState.currentHabit

    TextField(
        value = selectedHabit.name,
        onValueChange = { }
    )
}