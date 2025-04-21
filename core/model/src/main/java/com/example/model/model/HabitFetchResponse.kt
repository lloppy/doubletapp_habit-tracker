package com.example.model.model

import androidx.compose.ui.graphics.Color
import com.example.model.domain.Habit
import com.example.model.domain.HabitPriority
import com.example.model.domain.HabitType
import com.example.habittracker.model.toColor
import com.example.habittracker.model.toColorInt
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HabitFetchResponse(
    val color: Int? = null,
    val count: Int = 0,
    val date: Int = 0,
    val description: String = "",
    val frequency: Int = 0,
    val priority: Int = 0,
    val title: String = "",
    val type: Int = 0,
    val uid: String = "",
    @SerialName("done_dates") val doneDates: List<Int> = emptyList()
) {
    companion object {
        fun fromDomain(habit: Habit): HabitFetchResponse {
            return HabitFetchResponse(
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
                doneDates = emptyList()
            )
        }

        fun toDomain(habitResponse: HabitFetchResponse): Habit {
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