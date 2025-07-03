package com.example.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.data.local.mappers.Converters
import com.example.model.HabitCategory
import com.example.model.HabitPriority
import com.example.model.HabitType

@Entity(tableName = "habits")
@TypeConverters(Converters::class)
data class HabitEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val uid: String = "",
    val name: String = "",
    val description: String = "",
    val type: HabitType = HabitType.POSITIVE,
    val category: HabitCategory = HabitCategory.PRODUCTIVITY,
    val priority: HabitPriority = HabitPriority.MEDIUM,
    val frequency: String = "",
    @ColumnInfo("repeated_times") val repeatedTimes: Int = 1,
    val quantity: Int = 0,
    val colorHex: String = "#D3D3D3",
    val date: Long = System.currentTimeMillis()
)
