package com.example.habittracker.model.domain

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.example.habittracker.R
import com.example.habittracker.ui.theme.onErrorDark
import com.example.habittracker.ui.theme.onTertiaryDark

enum class HabitType(@StringRes val impactName: Int) {
    POSITIVE(R.string.positive),
    NEGATIVE(R.string.negative);

    fun getColor(): Color = when (this) {
        POSITIVE -> onTertiaryDark
        NEGATIVE -> onErrorDark
    }
}
