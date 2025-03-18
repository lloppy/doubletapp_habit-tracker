package com.example.habittracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.habittracker.ui.AppViewModelProvider
import com.example.habittracker.ui.screens.HabitTrackerApp
import com.example.habittracker.ui.screens.MainViewModel
import com.example.habittracker.ui.theme.AppTheme
import com.example.habittracker.ui.theme.HabitTrackerTheme
import com.example.habittracker.ui.theme.LocalThemeChange

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val viewModel: MainViewModel = viewModel(factory = AppViewModelProvider.Factory)
            val themeState by viewModel.themeState.collectAsState()

            val darkTheme = when (themeState) {
                AppTheme.MODE_AUTO -> DarkTheme(isSystemInDarkTheme())
                AppTheme.MODE_DAY -> DarkTheme(false)
                AppTheme.MODE_NIGHT -> DarkTheme(true)
            }

            CompositionLocalProvider(
                LocalTheme provides darkTheme,
                LocalThemeChange provides viewModel::setTheme
            ) {
                HabitTrackerTheme(isDarkTheme = LocalTheme.current.isDark) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        HabitTrackerApp()
                    }
                }
            }
        }
    }
}


data class DarkTheme(val isDark: Boolean = false)

val LocalTheme = compositionLocalOf { DarkTheme() }
