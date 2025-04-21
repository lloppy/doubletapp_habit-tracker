package com.example.domain.repository

import androidx.compose.runtime.MutableState

interface ThemeRepository {
    val themeState: MutableState<AppTheme>
    fun setTheme(theme: AppTheme)
}