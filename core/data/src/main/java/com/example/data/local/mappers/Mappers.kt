package com.example.data.local.mappers

import com.example.data.local.entity.HabitEntity
import com.example.model.Habit
import com.example.model.toHexString

fun HabitEntity.toDomain(): Habit = Habit(
    id = this.id,
    uid = this.uid,
    name = this.name,
    description = this.description,
    type = this.type,
    category = this.category,
    priority = this.priority,
    frequency = this.frequency,
    repeatedTimes = this.repeatedTimes,
    quantity = this.quantity,
    colorHex = this.colorHex,
    date = this.date
)

fun Habit.toEntity(): HabitEntity = HabitEntity(
    id = this.id,
    uid = this.uid,
    name = this.name,
    description = this.description,
    type = this.type,
    category = this.category,
    priority = this.priority,
    frequency = this.frequency,
    repeatedTimes = this.repeatedTimes,
    quantity = this.quantity,
    colorHex = this.color.toHexString(),
    date = this.date
)
