package com.example.habittracker.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habittracker.data.HabitsRepository
import com.example.habittracker.model.Habit
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HabitTrackerViewModel(
    private val repository: HabitsRepository
) : ViewModel() {
    val uiState: StateFlow<HabitTrackerState> =
        repository.getAllHabits().map {
            HabitTrackerState.Success(it)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = DELAY_FOR_KEEPING_INSTANCE_AFTER_CLOSING),
            initialValue = HabitTrackerState.Loading
        )

    suspend fun increaseRepeated(habitId: Int) {
        repository.increaseQuantity(id = habitId)
    }

    suspend fun decreaseRepeated(habitId: Int) {
        repository.decreaseQuantity(id = habitId)
    }

    suspend fun deleteItemById(habitId: Int) {
        repository.deleteById(id = habitId)
    }


    companion object {
        const val DELAY_FOR_KEEPING_INSTANCE_AFTER_CLOSING = 3_000L
    }
}

sealed interface HabitTrackerState {
    data class Success(val habits: List<Habit> = listOf()) : HabitTrackerState
    object Loading : HabitTrackerState
}
//TODO()вынести в отдельный файл стейты
//TODO()   плюс два списка  без стейта текущего номера экрана