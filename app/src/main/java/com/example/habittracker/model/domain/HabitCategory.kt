package com.example.habittracker.model.domain

import androidx.annotation.StringRes
import com.example.habittracker.R

enum class HabitCategory(@StringRes val categoryName: Int) {
    PRODUCTIVITY(R.string.productivity),
    SPORT(R.string.sport),
    STUDY(R.string.study),
    RELAXATION(R.string.relaxation),
    HEALTH(R.string.health);

    fun getEmoji(): String = when (this) {
        PRODUCTIVITY -> "\uD83D\uDD0B"
        SPORT -> "\uD83C\uDFC6"
        STUDY -> "\uD83E\uDDE0"
        RELAXATION -> "\uD83E\uDDD8"
        HEALTH -> "\uD83C\uDF3F"
    }
}
