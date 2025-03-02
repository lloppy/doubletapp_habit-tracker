package com.example.habittracker.ui.screens.item

import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.habittracker.R
import com.example.habittracker.ui.AppViewModelProvider
import com.example.habittracker.ui.screens.navigation.NavigationDestination

object HabitEditDestination : NavigationDestination {
    override val route = "edit_habit"
    override val title = R.string.item_edit_title
    const val itemIdArg = "itemId"
    val routeWithArgs = "$route/{$itemIdArg}"
}

@Composable
fun EditHabitScreen(
    navigateBack: () -> Boolean,
    modifier: Modifier = Modifier,
    viewModel: EditHabitViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val selectedHabit = viewModel.entryUiState.currentHabit

    TextField(
        value = selectedHabit.name,
        onValueChange = { }
    )
}