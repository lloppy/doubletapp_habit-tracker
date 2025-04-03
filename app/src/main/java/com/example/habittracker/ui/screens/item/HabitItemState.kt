package com.example.habittracker.ui.screens.item

import com.example.habittracker.ui.screens.item.create.HabitEntity

data class HabitItemState(
    val currentHabit: HabitEntity = HabitEntity(),
    val isEntryValid: Boolean = false,
)
