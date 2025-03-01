package com.example.habittracker.ui

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.habittracker.R
import com.example.habittracker.ui.screens.HabitTrackerScreen
import com.example.habittracker.ui.screens.HabitTrackerState
import com.example.habittracker.ui.screens.HabitTrackerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitTrackerApp(
    modifier: Modifier = Modifier
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val viewModel: HabitTrackerViewModel = viewModel()

     Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { TopAppBar(scrollBehavior) }
    ) { paddingValue ->

        HabitTrackerScreen(
            uiState = viewModel.uiState,
            onClickHabit = viewModel::onClickHabit,
            modifier = Modifier,
            contentPadding = paddingValue
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopAppBar(scrollBehavior: TopAppBarScrollBehavior, modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineSmall,
            )
        },
        scrollBehavior = scrollBehavior,
        modifier = modifier
    )
}
