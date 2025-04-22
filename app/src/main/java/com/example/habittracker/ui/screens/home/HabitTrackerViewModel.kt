package com.example.habittracker.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.DecreaseHabitQuantityUseCase
import com.example.domain.usecase.DeleteHabitByIdUseCase
import com.example.domain.usecase.GetAllHabitsUseCase
import com.example.domain.usecase.IncreaseHabitQuantityUseCase
import com.example.habittracker.model.FilterExpression
import com.example.habittracker.model.MultiplicationExpression
import com.example.habittracker.ui.shared.filter.FilterState
import com.example.habittracker.ui.shared.filter.toExpressions
import com.example.model.Habit
import com.example.model.HabitType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class HabitTrackerViewModel @Inject constructor(
    private val getAllHabitsUseCase: GetAllHabitsUseCase,
    private val increaseHabitQuantityUseCase: IncreaseHabitQuantityUseCase,
    private val decreaseHabitQuantityUseCase: DecreaseHabitQuantityUseCase,
    private val deleteHabitByIdUseCase: DeleteHabitByIdUseCase
) : ViewModel() {
    private val _filterState = MutableStateFlow(FilterState())

    val uiState: StateFlow<HabitTrackerState> = combine(
        getAllHabitsUseCase(),
        _filterState
    ) { habits, filterState ->
        val filteredHabits = applyFilters(
            habits,
            filterState.toExpressions()
        )

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
        increaseHabitQuantityUseCase(id = habitId)
    }

    suspend fun decreaseRepeated(habitId: Int) {
        decreaseHabitQuantityUseCase(id = habitId)
    }

    suspend fun delete(habitId: Int) {
        deleteHabitByIdUseCase(id = habitId)
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
