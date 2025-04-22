package com.example.habittracker.mappers

import androidx.compose.ui.graphics.Color
import com.example.habittracker.ui.theme.onErrorDark
import com.example.habittracker.ui.theme.onTertiaryDark
import com.example.model.HabitCategory
import com.example.model.HabitCategory.HEALTH
import com.example.model.HabitCategory.PRODUCTIVITY
import com.example.model.HabitCategory.RELAXATION
import com.example.model.HabitCategory.SPORT
import com.example.model.HabitCategory.STUDY
import com.example.model.HabitType
import com.example.model.HabitType.NEGATIVE
import com.example.model.HabitType.POSITIVE

fun HabitType.getColor(): Color = when (this) {
    POSITIVE -> onTertiaryDark
    NEGATIVE -> onErrorDark
}

fun HabitCategory.getEmoji(): String = when (this) {
    PRODUCTIVITY -> "\uD83D\uDD0B"
    SPORT -> "\uD83C\uDFC6"
    STUDY -> "\uD83E\uDDE0"
    RELAXATION -> "\uD83E\uDDD8"
    HEALTH -> "\uD83C\uDF3F"
}