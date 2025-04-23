package com.example.habittracker.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.DecreaseHabitQuantityUseCase
import com.example.domain.usecase.DeleteHabitByIdUseCase
import com.example.domain.usecase.GetAllHabitsUseCase
import com.example.domain.usecase.GetHabitByIdUseCase
import com.example.domain.usecase.IncreaseHabitQuantityUseCase
import com.example.domain.usecase.MarkHabitDoneUseCase
import com.example.domain.util.DataError
import com.example.domain.util.EmptyResult
import com.example.domain.util.onError
import com.example.domain.util.onSuccess
import com.example.habittracker.ui.shared.filter.FilterState
import com.example.habittracker.ui.shared.filter.toExpressions
import com.example.model.Habit
import com.example.model.HabitType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class HabitTrackerViewModel @Inject constructor(
    private val getAllHabitsUseCase: GetAllHabitsUseCase,
    private val increaseHabitQuantityUseCase: IncreaseHabitQuantityUseCase,
    private val decreaseHabitQuantityUseCase: DecreaseHabitQuantityUseCase,
    private val deleteHabitByIdUseCase: DeleteHabitByIdUseCase,
    private val markHabitDoneUseCase: MarkHabitDoneUseCase,
    private val getHabitByIdUseCase: GetHabitByIdUseCase,
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

    suspend fun increaseRepeated(
        habitId: Int,
        showMessage: (String) -> Unit,
    ) {
        handleHabitResult(
            habitId = habitId,
            result = increaseHabitQuantityUseCase(habitId),
            showMessage = showMessage
        )
    }

    suspend fun decreaseRepeated(
        habitId: Int,
        showMessage: (String) -> Unit,
    ) {
        handleHabitResult(
            habitId = habitId,
            result = decreaseHabitQuantityUseCase(habitId),
            showMessage = showMessage
        )
    }

    suspend fun delete(habitId: Int) {
        deleteHabitByIdUseCase(id = habitId)
    }

    suspend fun markChecked(habit: Habit) {
        markHabitDoneUseCase(habit = habit)
    }

    fun applyFilter(newFilterState: FilterState) {
        _filterState.value = newFilterState
    }

    private fun applyFilters(
        habits: List<Habit>,
        expressions: List<FilterExpression>,
    ): List<Habit> {
        return if (expressions.isEmpty()) {
            habits
        } else {
            MultiplicationExpression(expressions).interpret(habits)
        }
    }

    private suspend fun handleHabitResult(
        habitId: Int,
        result: EmptyResult<DataError>,
        showMessage: (String) -> Unit,
    ) {
        result.onSuccess {
            val habit = getHabitByIdUseCase(habitId).first()
            val message = when (habit.type) {
                HabitType.POSITIVE -> "Стоит выполнить это еще ${habit.repeatedTimes - habit.quantity} раз"
                HabitType.NEGATIVE -> "Можете выполнить это еще ${habit.repeatedTimes - habit.quantity} раз"
            }
            showMessage(message)
        }.onError { error ->
            val errorMessage = when (error) {
                DataError.Local.QUANTITY_EXCEEDED -> "Хватит это делать!"
                DataError.Local.MIN_QUANTITY_REACHED -> "You are breathtaking!"
                else -> "Unknown error"
            }
            showMessage(errorMessage)
        }
    }


    companion object {
        const val DELAY_FOR_KEEPING_INSTANCE_AFTER_CLOSING = 3_000L
    }
}
