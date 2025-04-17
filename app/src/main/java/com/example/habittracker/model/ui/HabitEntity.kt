package com.example.habittracker.model.ui

import androidx.compose.ui.graphics.Color
import com.example.habittracker.model.domain.Habit
import com.example.habittracker.model.domain.HabitCategory
import com.example.habittracker.model.domain.HabitPriority
import com.example.habittracker.model.domain.HabitType

data class HabitEntity(
    val id: Int = 0,
    val uid: String = "",
    val name: String = "",
    val description: String = "",
    val type: HabitType = HabitType.POSITIVE,
    val category: HabitCategory = HabitCategory.PRODUCTIVITY,
    val priority: HabitPriority = HabitPriority.MEDIUM,
    val frequency: String = "",
    val repeatedTimes: String = "",
    val quantity: String = "",
    val color: Color = Color.Yellow
)

fun HabitEntity.toHabit(): Habit = Habit(
    id = id,
    uid = uid,
    name = name,
    description = description,
    priority = priority,
    category = category,
    type = type,
    frequency = frequency,
    repeatedTimes = repeatedTimes.toIntOrNull() ?: 1,
    quantity = quantity.toIntOrNull() ?: 1,
    color = color
)

fun Habit.toUiState(): HabitEntity = HabitEntity(
    id = id,
    uid = uid,
    name = name,
    description = description,
    priority = priority,
    category = category,
    type = type,
    frequency = frequency,
    repeatedTimes = repeatedTimes.toString(),
    quantity = quantity.toString(),
    color = color
)