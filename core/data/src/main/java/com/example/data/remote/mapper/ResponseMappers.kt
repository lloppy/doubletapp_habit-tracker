package com.example.data.remote.mapper

import com.example.data.remote.model.HabitDoneResponseDto
import com.example.data.remote.model.HabitUidDto
import com.example.domain.model.HabitDoneResponseModel
import com.example.domain.model.HabitUidModel

fun HabitDoneResponseDto.toModel(): HabitDoneResponseModel = HabitDoneResponseModel(
    date = this.date,
    habitUid = this.habitUid
)

fun HabitDoneResponseModel.toDto(): HabitDoneResponseDto = HabitDoneResponseDto(
    date = this.date,
    habitUid = this.habitUid
)

fun HabitUidDto.toModel(): HabitUidModel = HabitUidModel(
    uid = this.uid
)

fun HabitUidModel.toDto(): HabitUidDto = HabitUidDto(
    uid = this.uid
)