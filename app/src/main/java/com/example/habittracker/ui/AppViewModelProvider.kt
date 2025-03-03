package com.example.habittracker.ui

import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.habittracker.data.FakeRepository
import com.example.habittracker.ui.screens.home.HabitTrackerViewModel
import com.example.habittracker.ui.screens.item.EditHabitViewModel
import com.example.habittracker.ui.screens.item.HabitEntryViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        val repository: FakeRepository = FakeRepository

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