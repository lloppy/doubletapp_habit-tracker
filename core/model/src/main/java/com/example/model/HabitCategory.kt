package com.example.model

import androidx.annotation.StringRes

enum class HabitCategory(@StringRes val categoryName: Int) {
    PRODUCTIVITY(R.string.productivity),
    SPORT(R.string.sport),
    STUDY(R.string.study),
    RELAXATION(R.string.relaxation),
    HEALTH(R.string.health);
}
