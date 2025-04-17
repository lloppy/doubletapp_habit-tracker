package com.example.habittracker.model.domain

import androidx.compose.ui.graphics.Color
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter


@ProvidedTypeConverter
class Converters {
    @TypeConverter
    fun habitTypeToString(habitCategory: HabitCategory): String {
        return habitCategory.name
    }

    @TypeConverter
    fun stringToHabitType(typeName: String): HabitCategory {
        return try {
            HabitCategory.valueOf(typeName)
        } catch (e: IllegalArgumentException) {
            HabitCategory.PRODUCTIVITY
        }
    }

    @TypeConverter
    fun habitPriorityToString(habitPriority: HabitPriority): String {
        return habitPriority.name
    }

    @TypeConverter
    fun stringToHabitPriority(priorityName: String): HabitPriority {
        return try {
            HabitPriority.valueOf(priorityName)
        } catch (e: IllegalArgumentException) {
            HabitPriority.MEDIUM
        }
    }

    @TypeConverter
    fun fromImpact(value: HabitType) = value.name

    @TypeConverter
    fun toImpact(value: String) = HabitType.valueOf(value)

    @TypeConverter
    fun colorToString(color: Color): String {
        return "${color.red},${color.green},${color.blue},${color.alpha}"
    }

    @TypeConverter
    fun stringToColor(colorString: String): Color {
        return try {
            val parts = colorString.split(",")
            Color(
                red = parts[0].toFloat(),
                green = parts[1].toFloat(),
                blue = parts[2].toFloat(),
                alpha = parts[3].toFloat()
            )
        } catch (e: Exception) {
            Color.LightGray
        }
    }
}