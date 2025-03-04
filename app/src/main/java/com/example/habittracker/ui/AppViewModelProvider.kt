package com.example.habittracker.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.habittracker.HabitTrackerApplication
import com.example.habittracker.data.ThemeRepository
import com.example.habittracker.ui.screens.MainViewModel
import com.example.habittracker.ui.screens.home.HabitTrackerViewModel
import com.example.habittracker.ui.screens.item.EditHabitViewModel
import com.example.habittracker.ui.screens.item.HabitEntryViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {

        initializer {
            MainViewModel(
                themeRepository = ThemeRepository(habitApplication().applicationContext)
            )
        }

        initializer {
            HabitTrackerViewModel(
                repository = habitApplication().container.habitsRepository
            )
        }

        initializer {
            HabitEntryViewModel(
                repository = habitApplication().container.habitsRepository
            )
        }

        initializer {
            EditHabitViewModel(
                this.createSavedStateHandle(),
                repository = habitApplication().container.habitsRepository
            )
        }
    }
}

fun CreationExtras.habitApplication(): HabitTrackerApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as HabitTrackerApplication)