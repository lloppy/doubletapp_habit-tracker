package com.example.data.repository

import android.content.Context
import com.example.domain.model.AppTheme
import com.example.domain.repository.ThemeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ThemeRepositoryImpl @Inject constructor(
    context: Context
) : ThemeRepository {

    private val sharedPreferences =
        context.getSharedPreferences(SHARED_PREF_THEME_KEY, Context.MODE_PRIVATE)

    override suspend fun getTheme(): AppTheme = withContext(Dispatchers.IO) {
        val themeName = sharedPreferences.getString(THEME_KEY, AppTheme.SYSTEM.name)
            ?: AppTheme.SYSTEM.name
        AppTheme.valueOf(themeName)
    }

    override suspend fun setTheme(theme: AppTheme) = withContext(Dispatchers.IO) {
        sharedPreferences.edit().putString(THEME_KEY, theme.name).apply()
    }

    companion object {
        private const val THEME_KEY = "theme"
        private const val SHARED_PREF_THEME_KEY = "theme_prefs"
    }
}
