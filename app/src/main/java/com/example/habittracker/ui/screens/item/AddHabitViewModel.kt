package com.example.habittracker.ui.screens.item

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.habittracker.data.FakeRepository

class AddHabitViewModel(
    private val repository: FakeRepository = FakeRepository()
) : ViewModel() {

    var entryUiState by mutableStateOf(HabitEntryState())
        private set

    init {

    }

    fun getDetailsFor(habitName: String) =
        repository.getSingleHabit(habitName)
            ?: throw IllegalArgumentException("Habit not found for name: $habitName")



}