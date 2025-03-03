package com.example.habittracker.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.habittracker.HabitTrackerApplication
import com.example.habittracker.data.FakeRepository
import com.example.habittracker.data.ThemeRepository
import com.example.habittracker.ui.screens.MainViewModel
import com.example.habittracker.ui.screens.home.HabitTrackerViewModel
import com.example.habittracker.ui.screens.item.EditHabitViewModel
import com.example.habittracker.ui.screens.item.HabitEntryViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        val repository: FakeRepository = FakeRepository


        initializer {
            MainViewModel(
                themeRepository = ThemeRepository(habitApplication().applicationContext)
            )
        }

        initializer {
            HabitTrackerViewModel(
                repository = repository
            )
        }

        initializer {
            HabitEntryViewModel(
                repository = repository
            )
        }

        initializer {
            EditHabitViewModel(
                this.createSavedStateHandle(),
                repository = repository
            )
        }

    }

}

fun CreationExtras.habitApplication(): HabitTrackerApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as HabitTrackerApplication)