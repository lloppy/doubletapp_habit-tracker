package com.example.habittracker.model

import androidx.compose.ui.graphics.Color
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.habittracker.ui.theme.onErrorDark
import com.example.habittracker.ui.theme.onTertiaryDark

@Entity(tableName = "habits")
data class Habit(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String = "",
    val description: String = "",

    @TypeConverters(Converters::class)
    val type: HabitType = HabitType.POSITIVE,
    @TypeConverters(Converters::class)
    val category: HabitCategory,
    @TypeConverters(Converters::class)
    val priority: HabitPriority = HabitPriority.MEDIUM,

    val frequency: String = "",
    @ColumnInfo("repeated_times")
    val repeatedTimes: Int = 1,
    val quantity: Int = 0,

    @TypeConverters(Converters::class)
    val color: Color = Color.LightGray
)


enum class HabitCategory(val categoryName: String) {
    SPORT("Спорт"),
    STUDY("Учеба"),
    RELAXATION("Отдых"),
    PRODUCTIVITY("Продуктивность"),
    HEALTH("Здоровье");

    fun getEmoji(): String = when (this) {
        SPORT -> "\uD83C\uDFC6"
        STUDY -> "\uD83E\uDDE0"
        RELAXATION -> "\uD83E\uDDD8"
        PRODUCTIVITY -> "\uD83D\uDD0B"
        HEALTH -> "\uD83C\uDF3F"
    }
}

enum class HabitPriority(val priorityName: String) {
    LOW("Низкий"),
    MEDIUM("Сердний"),
    HIGH("Высокий")
}

enum class HabitType(val impactName: String) {
    POSITIVE("Полезная"),
    NEGATIVE("Вредная");

    fun getColor(): Color = when (this) {
        POSITIVE -> onTertiaryDark
        NEGATIVE -> onErrorDark
    }
}

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