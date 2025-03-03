package com.example.habittracker.model

import androidx.compose.ui.graphics.Color
import java.util.UUID


data class Habit(
    val id: String = UUID.randomUUID().toString(),
    val name: String = "",
    val description: String = "",
    val type: HabitType,
    val priority: HabitPriority = HabitPriority.MEDIUM,
    val periodicity: HabitPeriodicity = HabitPeriodicity(),
    val color: Color = Color.LightGray
)

class HabitPeriodicity(
    val frequency: String = "",
    val repeatedTimes: Int = 1,
    val currentRepeated: Int = 0
)

enum class HabitType(val typeName: String, val emoji: String) {
    SPORT("Спорт", "\uD83C\uDFC6"),
    STUDY("Учеба", "\uD83E\uDDE0"),
    RELAXATION("Отдых","\uD83E\uDDD8"),
    PRODUCTIVITY("Продуктивность", "\uD83D\uDD0B"),
    HEALTH("Здоровье", "\uD83C\uDF3F")
}

enum class HabitPriority(val priorityName: String) {
    LOW("Низкий"),
    MEDIUM("Сердний"),
    HIGH("Высокий");
}
