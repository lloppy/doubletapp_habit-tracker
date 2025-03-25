package com.example.habittracker.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habittracker.data.HabitsRepository
import com.example.habittracker.model.HabitType
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HabitTrackerViewModel(
    private val repository: HabitsRepository
) : ViewModel() {
    val uiState: StateFlow<HabitTrackerState> =
        repository.getAllHabits().map { allHabits ->
            val positiveHabits = allHabits.filter { it.type == HabitType.POSITIVE }
            val negativeHabits = allHabits.filter { it.type == HabitType.NEGATIVE }

            HabitTrackerState.Success(
                habits = allHabits,
                positiveHabits = positiveHabits,
                negativeHabits = negativeHabits,
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = DELAY_FOR_KEEPING_INSTANCE_AFTER_CLOSING),
            initialValue = HabitTrackerState.Loading
        )

    suspend fun increaseRepeated(habitId: Int) {
        repository.increaseHabitQuantity(id = habitId)
    }

    suspend fun decreaseRepeated(habitId: Int) {
        repository.decreaseHabitQuantity(id = habitId)
    }

    suspend fun delete(habitId: Int) {
        repository.deleteByHabitId(id = habitId)
    }


    companion object {
        const val DELAY_FOR_KEEPING_INSTANCE_AFTER_CLOSING = 3_000L
    }
}
