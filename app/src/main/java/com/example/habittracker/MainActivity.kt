package com.example.habittracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.example.habittracker.ui.screens.HabitTrackerApp
import com.example.habittracker.ui.screens.MainViewModel
import com.example.habittracker.ui.theme.HabitTrackerTheme
import com.example.habittracker.ui.theme.LocalThemeChange
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val mainViewModel: MainViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as HabitTrackerApplication).applicationComponent.inject(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themeState by mainViewModel.themeState.collectAsState()

            CompositionLocalProvider(
                LocalThemeChange provides mainViewModel::setTheme
            ) {
                HabitTrackerTheme(appTheme = themeState) {
                    Surface(modifier = Modifier.fillMaxSize()) {
                        HabitTrackerApp(
                            appTheme = themeState,
                            viewModelFactory = viewModelFactory
                        )
                    }
                }
            }
        }
    }
}
