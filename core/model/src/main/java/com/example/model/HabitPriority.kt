package com.example.model

import androidx.annotation.StringRes

enum class HabitPriority(@StringRes val priorityName: Int) {
    LOW(R.string.low),
    MEDIUM(R.string.medium),
    HIGH(R.string.high)
}