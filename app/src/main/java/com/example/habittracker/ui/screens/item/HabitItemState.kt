package com.example.habittracker.ui.screens.item

data class HabitItemState(
    val currentHabit: HabitEntity = HabitEntity(),
    val isEntryValid: Boolean = false,
)
