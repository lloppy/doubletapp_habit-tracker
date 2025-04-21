package com.example.data.remote.mapper

import android.graphics.Color.blue
import android.graphics.Color.green
import android.graphics.Color.red
import androidx.compose.ui.graphics.Color
import com.example.model.domain.Habit
import com.example.model.domain.HabitPriority
import com.example.model.domain.HabitType
import com.example.model.model.HabitUpdateRequest

fun Habit.fromDomain(): HabitUpdateRequest {
    return HabitUpdateRequest(
        color = this.color.takeIf { this.color != Color.LightGray }?.toColorInt(),
        count = this.quantity,
        date = this.date,
        description = this.description,
        frequency = this.repeatedTimes,
        priority = when (this.priority) {
            HabitPriority.LOW -> 0
            HabitPriority.MEDIUM -> 1
            HabitPriority.HIGH -> 2
        },
        title = this.name,
        type = when (this.type) {
            HabitType.POSITIVE -> 0
            HabitType.NEGATIVE -> 1
        },
        uid = this.uid,
        doneDates = listOf(0)
    )
}

fun HabitUpdateRequest.toDomain(): Habit {
    return Habit(
        uid = this.uid,
        name = this.title,
        description = this.description,
        type = when (this.type) {
            0 -> HabitType.POSITIVE
            1 -> HabitType.NEGATIVE
            else -> HabitType.POSITIVE
        },
        priority = when (this.priority) {
            0 -> HabitPriority.LOW
            1 -> HabitPriority.MEDIUM
            2 -> HabitPriority.HIGH
            else -> HabitPriority.MEDIUM
        },
        color = this.color?.toColor() ?: Color.LightGray,
        repeatedTimes = this.frequency,
        quantity = this.count,
        date = this.date
    )
}

private fun Int.toColor(): Color {
    return Color(
        red = red(this) / 255f,
        green = green(this) / 255f,
        blue = blue(this) / 255f,
        alpha = 1f
    )
}
