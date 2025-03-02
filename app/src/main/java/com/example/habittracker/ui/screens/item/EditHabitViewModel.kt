package com.example.habittracker.ui.screens.item

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.habittracker.data.FakeRepository

class EditHabitViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: FakeRepository
) : ViewModel() {
    private val habitName: String = checkNotNull(savedStateHandle[HabitEditDestination.itemIdArg])

    var entryUiState by mutableStateOf(HabitEntryState())
        private set

    init {
        entryUiState = HabitEntryState(
            getDetailsFor(habitName).toUiState()
        )
    }
    fun getDetailsFor(habitName: String) =
        repository.getSingleHabit(habitName)
            ?: throw IllegalArgumentException("Habit not found for name: $habitName")



}

