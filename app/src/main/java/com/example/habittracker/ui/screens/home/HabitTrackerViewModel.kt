package com.example.habittracker.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habittracker.data.HabitsRepository
import com.example.habittracker.model.FilterExpression
import com.example.habittracker.model.Habit
import com.example.habittracker.model.HabitType
import com.example.habittracker.model.MultiplicationExpression
import com.example.habittracker.ui.shared.filter.FilterState
import com.example.habittracker.ui.shared.filter.toExpressions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class HabitTrackerViewModel(
    private val repository: HabitsRepository
) : ViewModel() {
    private val _filterState = MutableStateFlow(FilterState())

    val uiState: StateFlow<HabitTrackerState> = combine(
        repository.getAllHabits(),
        _filterState
    ) { habits, filterState ->
        val filteredHabits = applyFilters(habits, filterState.toExpressions())

        HabitTrackerState.Success(
            habits = filteredHabits,
            positiveHabits = filteredHabits.filter { it.type == HabitType.POSITIVE },
            negativeHabits = filteredHabits.filter { it.type == HabitType.NEGATIVE },
            filterState = filterState
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(DELAY_FOR_KEEPING_INSTANCE_AFTER_CLOSING),
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


    fun applyFilter(newFilterState: FilterState) {
        _filterState.value = newFilterState
    }

    private fun applyFilters(
        habits: List<Habit>,
        expressions: List<FilterExpression>
    ): List<Habit> {
        return if (expressions.isEmpty()) {
            habits
        } else {
            MultiplicationExpression(expressions).interpret(habits)
        }
    }

    companion object {
        const val DELAY_FOR_KEEPING_INSTANCE_AFTER_CLOSING = 3_000L
    }
}
