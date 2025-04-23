package com.example.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HabitUpdateRequestDto(
    val color: Int? = null,
    val count: Int = 0,
    val date: Long = 0,
    val description: String,
    @SerialName("done_dates") val doneDates: List<Int> = listOf(0),
    val frequency: Int,
    val priority: Int,
    val title: String,
    val type: Int,
    val uid: String = ""
)