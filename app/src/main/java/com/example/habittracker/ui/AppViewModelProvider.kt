package com.example.habittracker.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.habittracker.HabitTrackerApplication
import com.example.habittracker.ui.screens.MainViewModel
import com.example.habittracker.ui.screens.home.HabitTrackerViewModel
import com.example.habittracker.ui.screens.item.create.CreateHabitViewModel
import com.example.habittracker.ui.screens.item.edit.EditHabitViewModel
import com.example.habittracker.ui.screens.language.LanguageScreenViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {

        initializer {
            MainViewModel(
                themeRepository = habitApplication().container.themeRepository
            )
        }

        initializer {
            HabitTrackerViewModel(
                repository = habitApplication().container.habitsRepository
            )
        }

        initializer {
            CreateHabitViewModel(
                habitsRepository = habitApplication().container.habitsRepository,
                contextRepository = habitApplication().container.contextRepository
            )
        }

        initializer {
            EditHabitViewModel(
                savedStateHandle = this.createSavedStateHandle(),
                habitsRepository = habitApplication().container.habitsRepository,
                contextRepository = habitApplication().container.contextRepository
            )
        }

        initializer {
            LanguageScreenViewModel(
                languageRepository = habitApplication().container.languageRepository
            )
        }
    }
}

fun CreationExtras.habitApplication(): HabitTrackerApplication =
    this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as HabitTrackerApplication