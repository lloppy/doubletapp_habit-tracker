package com.example.model

import androidx.compose.ui.graphics.Color

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
    val date: Long = System.currentTimeMillis(),
) {
    val color = colorHex.toComposeColor()
}

fun Color.toHexString(): String {
    val argb = this.value.toLong()
    return String.format("#%08X", argb)
}

fun String.toComposeColor(): Color = try {
    val hex = this.removePrefix("#")
    val colorLong = when (hex.length) {
        6 -> hex.toLong(16) or 0x00000000FF000000
        8 -> hex.toLong(16)
        else -> throw IllegalArgumentException("color format exception")
    }
    Color(colorLong)
} catch (e: Exception) {
    Color.Unspecified
}
