package com.example.domain.repository

import com.example.model.AppTheme

interface ThemeRepository {
    suspend fun getTheme(): AppTheme
    suspend fun setTheme(theme: AppTheme)
}
