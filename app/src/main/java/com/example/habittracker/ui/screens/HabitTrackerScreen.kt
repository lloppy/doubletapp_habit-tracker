package com.example.habittracker.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.example.habittracker.R
import com.example.habittracker.model.Habit

@Composable
fun HabitTrackerScreen(
    uiState: HabitTrackerState,
    onClickHabit: () -> Unit,
    modifier: Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    when (uiState) {
        is HabitTrackerState.Loading -> {
            LoadingScreen()
        }

        is HabitTrackerState.Success -> {
            HabitContent(
                habits = uiState.habits,
                onClickHabit = onClickHabit,
                modifier = modifier,
                contentPadding = contentPadding
            )
        }
    }
}

@Composable
fun HabitContent(
    habits: List<Habit>,
    onClickHabit: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues
) {
    LazyColumn(modifier = modifier.padding(contentPadding)) {
        items(items = habits, key = { it.name }) { habit ->
            HabitCard(
                habit = habit,
                onClickHabit = onClickHabit,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_small))
                    .aspectRatio(1.5f)
            )
        }
    }
}

@Composable
fun HabitCard(habit: Habit, onClickHabit: () -> Unit, modifier: Modifier) {
    Card(modifier = modifier, onClick = onClickHabit) {
        Text(text = habit.name)
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(modifier = Modifier.size(dimensionResource(R.dimen.loading_circle)))
    }
}
