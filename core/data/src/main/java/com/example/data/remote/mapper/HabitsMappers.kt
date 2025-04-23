package com.example.data.remote.mapper

import android.graphics.Color
import com.example.data.remote.model.HabitFetchResponseDto
import com.example.data.remote.model.HabitUpdateRequestDto
import com.example.model.Habit
import com.example.model.HabitPriority
import com.example.model.HabitType

fun Habit.toDto(): HabitUpdateRequestDto {
    return HabitUpdateRequestDto(
        color = this.colorHex.toIntColor(),
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

fun HabitFetchResponseDto.toEntity(): Habit {
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
        colorHex = this.color?.toHexColor() ?: "#D3D3D3",
        repeatedTimes = this.frequency,
        quantity = this.count,
        date = this.date
    )
}

fun String?.toIntColor(): Int {
    return if (this.isNullOrEmpty()) {
        0xFFCCCCCC.toInt()
    } else {
        Color.parseColor(this)
    }
}

fun Int.toHexColor(): String {
    return String.format("#%06X", 0xFFFFFF and this)
}