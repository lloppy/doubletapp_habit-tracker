package com.example.habittracker.ui.screens.item

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habittracker.data.FakeRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HabitDetailViewModel(
    savedStateHandle: SavedStateHandle,
    repository: FakeRepository
) : ViewModel() {
    private val habitId: String = checkNotNull(savedStateHandle[HabitDetailDestination.itemIdArg])

    val uiState = repository.getSingleHabit(habitId)
        .filterNotNull()
        .map { HabitDetailsUiState(habitDetails = it.toUiState()) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = HabitDetailsUiState()
        )


}

data class HabitDetailsUiState(val habitDetails: HabitDetails = HabitDetails())
