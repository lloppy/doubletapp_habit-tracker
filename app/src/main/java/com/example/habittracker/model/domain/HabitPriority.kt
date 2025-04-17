package com.example.habittracker.model.domain

import androidx.annotation.StringRes
import com.example.habittracker.R

enum class HabitPriority(@StringRes val priorityName: Int) {
    LOW(R.string.low),
    MEDIUM(R.string.medium),
    HIGH(R.string.high)
}