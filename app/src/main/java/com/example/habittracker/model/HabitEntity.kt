package com.example.habittracker.model

import androidx.compose.ui.graphics.Color
import com.example.model.Habit
import com.example.model.HabitCategory
import com.example.model.HabitPriority
import com.example.model.HabitType
import com.example.model.toHexString

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
    val color: Color = Color.Yellow,
)

fun HabitEntity.toHabit(): Habit = Habit(
    id = this.id,
    uid = this.uid,
    name = this.name,
    description = this.description,
    priority = this.priority,
    category = this.category,
    type = this.type,
    frequency = this.frequency,
    repeatedTimes = this.repeatedTimes.toIntOrNull() ?: 1,
    quantity = this.quantity.toIntOrNull() ?: 1,
    colorHex = this.color.toHexString()
)

fun Habit.toUiState(): HabitEntity = HabitEntity(
    id = this.id,
    uid = this.uid,
    name = this.name,
    description = this.description,
    priority = this.priority,
    category = this.category,
    type = this.type,
    frequency = this.frequency,
    repeatedTimes = this.repeatedTimes.toString(),
    quantity = this.quantity.toString(),
    color = this.color
)
