package com.example.habittracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.habittracker.ui.AppViewModelProvider
import com.example.habittracker.ui.screens.HabitTrackerApp
import com.example.habittracker.ui.screens.MainViewModel
import com.example.habittracker.ui.theme.HabitTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val viewModel: MainViewModel = viewModel(factory = AppViewModelProvider.Factory)
            val themeState by viewModel.themeState.collectAsState()

            HabitTrackerTheme(appTheme = themeState) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    HabitTrackerApp()
                }
            }

        }
    }
}

// посмотерть
data class DarkTheme(val isDark: Boolean = false)

val LocalTheme = compositionLocalOf { DarkTheme() }
