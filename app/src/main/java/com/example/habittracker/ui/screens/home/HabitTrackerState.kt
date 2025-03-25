package com.example.habittracker.ui.screens.home

import com.example.habittracker.model.Habit

sealed interface HabitTrackerState {
    data class Success(
        val habits: List<Habit> = listOf(),
        val positiveHabits: List<Habit> = listOf(),
        val negativeHabits: List<Habit> = listOf()
    ) : HabitTrackerState

    object Loading : HabitTrackerState
}
