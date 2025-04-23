package com.example.habittracker.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.union
import androidx.compose.foundation.layout.windowInsetsPadding
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.habittracker.R
import com.example.habittracker.ui.navigation.NavigationDestination
import com.example.habittracker.ui.screens.HabitAppBar
import com.example.habittracker.ui.screens.home.components.HabitCard
import com.example.habittracker.ui.shared.filter.FilterModalSheet
import com.example.habittracker.ui.shared.pager.HabitPager
import com.example.habittracker.ui.shared.pager.PageType
import com.example.habittracker.ui.theme.Spacing
import com.example.model.Habit
import kotlinx.coroutines.launch

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val title = R.string.app_name
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitTrackerScreen(
    viewModel: HabitTrackerViewModel,
    onClickAddItem: () -> Unit,
    onClickHabit: (Int) -> Unit,
    onClickOpenDrawer: () -> Unit,
    modifier: Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    var showBottomSheet by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            HabitAppBar(
                title = stringResource(HomeDestination.title),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
                onClickOpenDrawer = onClickOpenDrawer,
                onClickFilter = { showBottomSheet = true }
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
        modifier = modifier
            .windowInsetsPadding(WindowInsets.displayCutout.union(WindowInsets.navigationBars))
            .nestedScroll(scrollBehavior.nestedScrollConnection)
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

                    HabitContent(
                        filteredHabits = when (selectedPageType) {
                            PageType.ALL -> state.habits
                            PageType.ONLY_POSITIVE -> state.positiveHabits
                            PageType.ONLY_NEGATIVE -> state.negativeHabits
                        },
                        onCheckClick = {
                            coroutineScope.launch {
                                viewModel.markChecked(it)
                            }
                        },
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

                    if (showBottomSheet) {
                        FilterModalSheet(
                            onDismiss = { showBottomSheet = false },
                            onSubmit = { newFilterState ->
                                viewModel.applyFilter(newFilterState)
                            },
                            initialFilterState = state.filterState
                        )
                    }
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
    onCheckClick: (Habit) -> Unit,
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
            items(items = filteredHabits, key = { it.id }) { habit ->
                HabitCard(
                    habit = habit,
                    onCheckClick = { onCheckClick(habit) },
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
