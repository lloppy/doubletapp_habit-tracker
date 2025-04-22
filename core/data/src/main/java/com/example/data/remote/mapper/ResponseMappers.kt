package com.example.data.remote.mapper

import com.example.domain.entity.HabitDoneResponse
import com.example.domain.entity.HabitUid


fun HabitDoneResponse.toDomain(): HabitDoneResponse = HabitDoneResponse(
    date = this.date,
    habitUid = this.habitUid
)

fun HabitDoneResponse.toData(): HabitDoneResponse = HabitDoneResponse(
    date = this.date,
    habitUid = this.habitUid
)


fun HabitUid.toDomain(): HabitUid = HabitUid(
    uid = this.uid
)

fun HabitUid.toData(): HabitUid = HabitUid(
    uid = this.uid
)