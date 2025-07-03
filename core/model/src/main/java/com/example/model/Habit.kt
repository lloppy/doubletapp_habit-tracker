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
    val color: Color = colorHex.toComposeColor()
}

fun Color.toHexString(): String {
    val red = this.red * 255
    val green = this.green * 255
    val blue = this.blue * 255
    return String.format("#%02x%02x%02x", red.toInt(), green.toInt(), blue.toInt())
}

fun String.toComposeColor(): Color = try {
    Color(android.graphics.Color.parseColor(this))
} catch (e: Exception) {
    Color.Unspecified
}
