package com.example.habittracker.data

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.habittracker.ui.theme.AppTheme


class ThemeRepository(private val context: Context) {

    private val sharedPreferences = context.getSharedPreferences(SHAR_PREF_THEME_KEY, Context.MODE_PRIVATE)

    private val _themeState = mutableStateOf(
        AppTheme.valueOf(sharedPreferences.getString(THEME_KEY, AppTheme.MODE_AUTO.name) ?: AppTheme.MODE_AUTO.name)
    )
    val themeState: MutableState<AppTheme> = _themeState

    fun setTheme(theme: AppTheme) {
        _themeState.value = theme
        sharedPreferences.edit().putString(THEME_KEY, theme.name).apply()
    }

    companion object{
        const val THEME_KEY = "theme"
        const val SHAR_PREF_THEME_KEY = "theme_prefs"
    }
}


