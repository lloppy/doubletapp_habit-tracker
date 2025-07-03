package com.example.data.local.mappers

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.model.HabitCategory
import com.example.model.HabitPriority
import com.example.model.HabitType

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

}