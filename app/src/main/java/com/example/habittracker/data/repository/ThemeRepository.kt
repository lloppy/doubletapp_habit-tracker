package com.example.habittracker.data.repository

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.habittracker.ui.theme.AppTheme

interface ThemeRepository {
    val themeState: MutableState<AppTheme>
    fun setTheme(theme: AppTheme)
}

class ThemeRepositoryImpl(context: Context) : ThemeRepository {

    private val sharedPreferences =
        context.getSharedPreferences(SHAR_PREF_THEME_KEY, Context.MODE_PRIVATE)

    private val _themeState = mutableStateOf(
        AppTheme.valueOf(
            sharedPreferences.getString(THEME_KEY, AppTheme.MODE_AUTO.name)
                ?: AppTheme.MODE_AUTO.name
        )
    )
    override val themeState: MutableState<AppTheme> = _themeState

    override fun setTheme(theme: AppTheme) {
        _themeState.value = theme
        sharedPreferences.edit().putString(THEME_KEY, theme.name).apply()
    }

    companion object {
        const val THEME_KEY = "theme"
        const val SHAR_PREF_THEME_KEY = "theme_prefs"
    }
}
