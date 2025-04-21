package com.example.model.model

import android.util.Log
import androidx.compose.ui.graphics.Color
import com.example.model.domain.Habit
import com.example.model.domain.HabitPriority
import com.example.model.domain.HabitType
import com.example.habittracker.model.toColor
import com.example.habittracker.model.toColorInt
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HabitUpdateRequest(
    val color: Int? = null,
    val count: Int = 0,
    val date: Int = 0,
    val description: String,
    @SerialName("done_dates") val doneDates: List<Int> = listOf(0),
    val frequency: Int,
    val priority: Int,
    val title: String,
    val type: Int,
    val uid: String = ""
)