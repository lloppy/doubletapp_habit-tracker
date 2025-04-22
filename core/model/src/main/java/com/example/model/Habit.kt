package com.example.model

data class Habit(
    val id: Int = 0,
    val uid: String = "",
    val name: String = "",
    val description: String = "",
    val type: HabitType = HabitType.POSITIVE,
    val category: HabitCategory = HabitCategory.PRODUCTIVITY,
    val priority: HabitPriority = HabitPriority.MEDIUM,
    val frequency: String = "",
    val repeatedTimes: Int = 1,
    val quantity: Int = 0,
    val colorHex: String = "#D3D3D3",
    val date: Long = System.currentTimeMillis()
)