package com.example.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HabitFetchResponse(
    val color: Int? = null,
    val count: Int = 0,
    val date: Long = 0,
    val description: String = "",
    val frequency: Int = 0,
    val priority: Int = 0,
    val title: String = "",
    val type: Int = 0,
    val uid: String = "",
    @SerialName("done_dates") val doneDates: List<Int> = emptyList()
)