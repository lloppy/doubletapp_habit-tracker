package com.example.habittracker.model

import androidx.compose.ui.graphics.Color


data class Habit(
    val name: String = "",
    val description: String = "",
    val priority: HabitPriority = HabitPriority.MEDIUM,
    val type: HabitType = HabitType.NOT_SELECTED,
    val periodicity: HabitPeriodicity = HabitPeriodicity(""),
    val color: Color = Color.LightGray
)

class HabitPeriodicity(
    val frequency: String,
    val daysOfWeek: List<String> = emptyList()
)

enum class HabitType {
    NOT_SELECTED,
    SPORT,
    STUDY,
    RELAXATION,
    PRODUCTIVITY
}

enum class HabitPriority(val priorityName: String) {
    LOW("Низкий"),
    MEDIUM("Сердний"),
    HIGH("Высокий");
}
