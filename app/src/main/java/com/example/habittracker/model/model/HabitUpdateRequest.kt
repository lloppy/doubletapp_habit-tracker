package com.example.habittracker.model.model

import android.util.Log
import androidx.compose.ui.graphics.Color
import com.example.habittracker.model.domain.Habit
import com.example.habittracker.model.domain.HabitPriority
import com.example.habittracker.model.domain.HabitType
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
) {
    companion object {
        fun fromDomain(habit: Habit): HabitUpdateRequest {
            return HabitUpdateRequest(
                color = habit.color.takeIf { it != Color.LightGray }?.toColorInt(),
                count = habit.quantity,
                date = habit.date,
                description = habit.description,
                frequency = habit.repeatedTimes,
                priority = when (habit.priority) {
                    HabitPriority.LOW -> 0
                    HabitPriority.MEDIUM -> 1
                    HabitPriority.HIGH -> 2
                },
                title = habit.name,
                type = when (habit.type) {
                    HabitType.POSITIVE -> 0
                    HabitType.NEGATIVE -> 1
                },
                uid = habit.uid,
                doneDates = listOf(0)
            ).also {
                Log.e("SharedHabitsRepository", it.toString())
            }
        }

        fun toDomain(habitResponse: HabitUpdateRequest): Habit {
            return Habit(
                uid = habitResponse.uid,
                name = habitResponse.title,
                description = habitResponse.description,
                type = when (habitResponse.type) {
                    0 -> HabitType.POSITIVE
                    1 -> HabitType.NEGATIVE
                    else -> HabitType.POSITIVE
                },
                priority = when (habitResponse.priority) {
                    0 -> HabitPriority.LOW
                    1 -> HabitPriority.MEDIUM
                    2 -> HabitPriority.HIGH
                    else -> HabitPriority.MEDIUM
                },
                color = habitResponse.color?.toColor() ?: Color.LightGray,
                repeatedTimes = habitResponse.frequency,
                quantity = habitResponse.count,
                date = habitResponse.date
            )
        }
    }
}