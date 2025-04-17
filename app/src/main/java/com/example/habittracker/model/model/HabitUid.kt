package com.example.habittracker.model.model

import kotlinx.serialization.Serializable

@Serializable
sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error(val code: Int, val message: String) : ApiResult<Nothing>()
}

@Serializable
data class HabitUid(
    val uid: String
)
