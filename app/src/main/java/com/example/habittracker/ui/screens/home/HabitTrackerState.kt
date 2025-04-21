package com.example.habittracker.ui.screens.home

import com.example.model.domain.Habit
import com.example.habittracker.ui.shared.filter.FilterState

sealed interface HabitTrackerState {
    data class Success(
        val habits: List<com.example.model.domain.Habit> = listOf(),
        val positiveHabits: List<com.example.model.domain.Habit> = listOf(),
        val negativeHabits: List<com.example.model.domain.Habit> = listOf(),
        val filterState: FilterState
    ) : HabitTrackerState

    object Loading : HabitTrackerState
}
