package com.example.habittracker.ui.screens

import com.example.habittracker.model.Habit

sealed interface HabitTrackerState{
    data class Success(val habits: List<Habit> ): HabitTrackerState
    object Loading: HabitTrackerState
}