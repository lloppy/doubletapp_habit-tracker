package com.example.habittracker.model

import androidx.compose.ui.graphics.Color

data class HabitEntity(
    val id: Int = 0,
    val uid: String = "",
    val name: String = "",
    val description: String = "",
    val type: com.example.model.domain.HabitType = com.example.model.domain.HabitType.POSITIVE,
    val category: com.example.model.domain.HabitCategory = com.example.model.domain.HabitCategory.PRODUCTIVITY,
    val priority: com.example.model.domain.HabitPriority = com.example.model.domain.HabitPriority.MEDIUM,
    val frequency: String = "",
    val repeatedTimes: String = "",
    val quantity: String = "",
    val color: Color = Color.Yellow
)

fun HabitEntity.toHabit(): com.example.model.domain.Habit = com.example.model.domain.Habit(
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

fun com.example.model.domain.Habit.toUiState(): HabitEntity = HabitEntity(
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