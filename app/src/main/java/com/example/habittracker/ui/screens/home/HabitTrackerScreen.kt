package com.example.habittracker.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.habittracker.R
import com.example.habittracker.model.Habit
import com.example.habittracker.ui.AppViewModelProvider
import com.example.habittracker.ui.navigation.NavigationDestination
import com.example.habittracker.ui.screens.HabitAppBar
import com.example.habittracker.ui.screens.home.components.HabitCard
import com.example.habittracker.ui.shared.pager.HabitPager
import com.example.habittracker.ui.shared.pager.PageType
import com.example.habittracker.ui.theme.Spacing
import kotlinx.coroutines.launch

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val title = R.string.app_name
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitTrackerScreen(
    onClickAddItem: () -> Unit,
    onClickHabit: (Int) -> Unit,
    onClickOpenDrawer: () -> Unit,
    modifier: Modifier,
    viewModel: HabitTrackerViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        topBar = {
            HabitAppBar(
                title = stringResource(HomeDestination.title),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
                onClickOpenDrawer = onClickOpenDrawer
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onClickAddItem,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(Spacing.large)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.item_entry_title)
                )
            }
        },
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { paddingValue ->

        when (val state = uiState) {
            is HabitTrackerState.Loading -> {
                LoadingScreen()
            }

            is HabitTrackerState.Success -> {
                HabitPager(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(paddingValue)
                ) { selectedPageType ->

                    val filteredHabits = when (selectedPageType) {
                        PageType.ALL -> state.habits
                        PageType.ONLY_POSITIVE -> state.positiveHabits
                        PageType.ONLY_NEGATIVE -> state.negativeHabits
                    }

                    HabitContent(
                        filteredHabits = filteredHabits,
                        onIncreaseRepeated = {
                            coroutineScope.launch {
                                viewModel.increaseRepeated(it)
                            }
                        },
                        onDecreaseRepeated = {
                            coroutineScope.launch {
                                viewModel.decreaseRepeated(it)
                            }
                        },
                        onClickHabit = {
                            coroutineScope.launch {
                                onClickHabit.invoke(it)
                            }
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
fun HabitContent(
    filteredHabits: List<Habit>,
    onClickHabit: (Int) -> Unit,
    modifier: Modifier = Modifier,
    onIncreaseRepeated: (Int) -> Unit,
    onDecreaseRepeated: (Int) -> Unit,
) {
    if (filteredHabits.isEmpty()) {
        Box(modifier = modifier, contentAlignment = Alignment.Center) {
            Text(
                text = stringResource(R.string.no_items),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(dimensionResource(R.dimen.min_habit_card_width)),
            modifier = modifier,
            verticalArrangement = Arrangement.Top
        ) {
            items(items = filteredHabits, key = { it.name }) { habit ->
                HabitCard(
                    habit = habit,
                    onIncreaseRepeated = { onIncreaseRepeated(habit.id) },
                    onDecreaseRepeated = { onDecreaseRepeated(habit.id) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Spacing.small)
                        .aspectRatio(4f)
                        .clickable(onClick = { onClickHabit(habit.id) })
                )
            }
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(modifier = Modifier.size(Spacing.loading))
    }
}
