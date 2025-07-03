package com.example.habittracker.ui.screens.home

import com.example.habittracker.ui.shared.filter.FilterState
import com.example.model.Habit

sealed interface HabitTrackerState {
    data class Success(
        val habits: List<Habit> = listOf(),
        val positiveHabits: List<Habit> = listOf(),
        val negativeHabits: List<Habit> = listOf(),
        val filterState: FilterState
    ) : HabitTrackerState

    object Loading : HabitTrackerState
}
