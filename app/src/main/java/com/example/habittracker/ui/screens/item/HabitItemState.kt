package com.example.habittracker.ui.screens.item

import com.example.habittracker.model.ui.HabitEntity

data class HabitItemState(
    val currentHabit: HabitEntity = HabitEntity(),
    val isEntryValid: Boolean = false,
)
