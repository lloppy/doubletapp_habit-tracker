package com.example.model.domain

import androidx.compose.ui.graphics.Color
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "habits")
@TypeConverters(Converters::class)
data class Habit(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val uid: String = "",
    val name: String = "",
    val description: String = "",
    val type: HabitType = HabitType.POSITIVE,
    val category: HabitCategory = HabitCategory.PRODUCTIVITY,
    val priority: HabitPriority = HabitPriority.MEDIUM,
    val frequency: String = "",
    @ColumnInfo("repeated_times") val repeatedTimes: Int = 1,
    val quantity: Int = 1,
    val color: Color = Color.LightGray,
    val date: Int = (System.currentTimeMillis() / 1000).toInt()
)