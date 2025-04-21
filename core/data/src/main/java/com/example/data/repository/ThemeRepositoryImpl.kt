package com.example.data.repository

import android.content.Context
import com.example.domain.repository.ThemeRepository


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
