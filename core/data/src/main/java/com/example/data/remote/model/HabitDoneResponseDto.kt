package com.example.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HabitDoneResponseDto(
    val date: Long,
    @SerialName("habit_uid") val habitUid: String
)