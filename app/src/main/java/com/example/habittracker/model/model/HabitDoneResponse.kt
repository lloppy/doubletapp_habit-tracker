package com.example.habittracker.model.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HabitDoneResponse(
    val date: Int,
    @SerialName("habit_uid") val habitUid: String,
)