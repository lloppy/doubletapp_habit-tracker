package com.example.habittracker.ui.screens.item

import com.example.habittracker.model.HabitEntity

data class HabitItemState(
    val currentHabit: HabitEntity = HabitEntity(),
    val isEntryValid: Boolean = false,
)
