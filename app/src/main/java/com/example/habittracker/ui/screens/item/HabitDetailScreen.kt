package com.example.habittracker.ui.screens.item

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.example.habittracker.R
import com.example.habittracker.model.Habit
import com.example.habittracker.ui.HabitAppBar
import com.example.habittracker.ui.screens.navigation.NavigationDestination


object HabitDetailDestination: NavigationDestination {
    override val route = "detail_habit"
    override val title = R.string.detail
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitDetailScreen(
    habitName: String,
    habit: Habit?,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        topBar = {
            HabitAppBar(
                title = habit?.name.toString(),
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        },
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { paddingValue ->

        Column(modifier = modifier.padding(paddingValue)) {
            Text(text = habitName)
        }
    }
}