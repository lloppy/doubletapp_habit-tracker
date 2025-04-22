package com.example.model

import androidx.annotation.StringRes

enum class HabitType(@StringRes val impactName: Int) {
    POSITIVE(R.string.positive),
    NEGATIVE(R.string.negative);
}
