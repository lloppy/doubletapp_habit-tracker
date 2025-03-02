package com.example.habittracker.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habittracker.data.FakeRepository
import com.example.habittracker.model.Habit
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HabitTrackerViewModel(
    repository: FakeRepository
): ViewModel() {
    val uiState: StateFlow<HabitTrackerState> =
        repository.habits.map {
            HabitTrackerState.Success(it)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = DELAY_FOR_IMITATE_LOADING),
            initialValue = HabitTrackerState.Loading

        )

    companion object {
        const val DELAY_FOR_IMITATE_LOADING = 1_000L
    }
}

sealed interface HabitTrackerState{
    data class Success(val habits: List<Habit> = listOf()): HabitTrackerState
    object Loading: HabitTrackerState
}