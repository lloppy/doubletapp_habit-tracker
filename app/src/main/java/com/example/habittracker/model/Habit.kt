package com.example.habittracker.model

import androidx.compose.ui.graphics.Color


data class Habit(
    val name: String = "",
    val description: String = "",
    val type: HabitType,
    val priority: HabitPriority = HabitPriority.MEDIUM,
    val periodicity: HabitPeriodicity = HabitPeriodicity(""),
    val color: Color = Color.LightGray
)

class HabitPeriodicity(
    val frequency: String,
    val repeatedTimes: Int = 1
)

enum class HabitType(val typeName: String) {
    SPORT("Спорт"),
    STUDY("Учеба"),
    RELAXATION("Отдых"),
    PRODUCTIVITY("Продуктивность")
}

enum class HabitPriority(val priorityName: String) {
    LOW("Низкий"),
    MEDIUM("Сердний"),
    HIGH("Высокий");
}
