package com.example.habittracker.ui.screens.item

import com.example.model.HabitCategory
import com.example.model.HabitPriority
import com.example.model.HabitType

sealed interface UpdateAction {
    data class Name(val name: String) : UpdateAction
    data class Frequency(val frequency: String) : UpdateAction
    data class Description(val description: String) : UpdateAction
    data class Category(val category: HabitCategory) : UpdateAction
    data class Color(val color: androidx.compose.ui.graphics.Color) : UpdateAction
    data class Priority(val priority: HabitPriority) : UpdateAction
    data class Type(val type: HabitType) : UpdateAction
    data class RepeatedTimes(val times: String) : UpdateAction
}