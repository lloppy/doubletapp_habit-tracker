package com.example.habittracker.ui.screens.item

import com.example.model.domain.HabitCategory
import com.example.model.domain.HabitPriority
import com.example.model.domain.HabitType

sealed interface UpdateAction {
    data class Name(val name: String) : UpdateAction
    data class Frequency(val frequency: String) : UpdateAction
    data class Description(val description: String) : UpdateAction
    data class Category(val category: com.example.model.domain.HabitCategory) : UpdateAction
    data class Color(val color: androidx.compose.ui.graphics.Color) : UpdateAction
    data class Priority(val priority: com.example.model.domain.HabitPriority) : UpdateAction
    data class Type(val type: com.example.model.domain.HabitType) : UpdateAction
    data class RepeatedTimes(val times: String) : UpdateAction
}