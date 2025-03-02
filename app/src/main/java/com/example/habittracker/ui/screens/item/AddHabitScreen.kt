package com.example.habittracker.ui.screens.item

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.habittracker.R
import com.example.habittracker.ui.AppViewModelProvider
import com.example.habittracker.ui.HabitAppBar
import com.example.habittracker.ui.screens.navigation.NavigationDestination

object AddHabitDestination : NavigationDestination {
    override val route = "add_habit"
    override val title = R.string.create_habit
    const val itemIdArg = "itemId"
    val routeWithArgs = "$route/{$itemIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddHabitScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AddHabitViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        topBar = {
            HabitAppBar(
                title = stringResource(AddHabitDestination.title),
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        },
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { paddingValue ->

        Column(modifier = modifier.padding(paddingValue)) {

            Text(text = AddHabitDestination.route)
            TextField(
                value = "new",
                onValueChange = { }
            )
        }
    }


}