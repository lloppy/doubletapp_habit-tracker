package com.example.habittracker.ui.screens.item

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.habittracker.data.HabitsRepository
import com.example.habittracker.model.Habit
import com.example.habittracker.model.HabitPriority
import com.example.habittracker.model.HabitType

data class HabitEntryState(
    val currentHabit: HabitEntity = HabitEntity(),
    val isEntryValid: Boolean = false,
)

class HabitEntryViewModel(
    private val repository: HabitsRepository
) : ViewModel() {

    var entryUiState by mutableStateOf(HabitEntryState())
        private set

    fun updateUiState(newHabit: HabitEntity) {
        entryUiState = HabitEntryState(
            currentHabit = newHabit,
            isEntryValid = validateInput(newHabit)
        )
    }

    private fun validateInput(uiState: HabitEntity = entryUiState.currentHabit): Boolean =
        with(uiState) { name.isNotBlank() && type.isNotBlank() && canParseInt(uiState.repeatedTimes) }


    private fun canParseInt(repeatedTimes: String): Boolean =
        with(repeatedTimes) { toIntOrNull() != null || isBlank() }


    suspend fun saveItem() {
        if (validateInput()) {
            repository.insert(habit = entryUiState.currentHabit.toHabit())
        }
    }
}

data class HabitEntity(
    val id: Int = 0,
    val name: String = "",
    val description: String = "",
    val type: String = "",
    val priority: String = "",
    val frequency: String = "",
    val repeatedTimes: String = "",
    val quantity: String = "",
    val color: Color = Color.Yellow
)

fun HabitEntity.toHabit(): Habit = Habit(
    id = id,
    name = name,
    description = description,

    priority = HabitPriority.entries.firstOrNull { it.priorityName == priority }
        ?: HabitPriority.MEDIUM,
    type = HabitType.entries.firstOrNull { it.typeName == type }
        ?: HabitType.PRODUCTIVITY,

    frequency = frequency,
    repeatedTimes = repeatedTimes.toIntOrNull() ?: 1,
    quantity = quantity.toIntOrNull() ?: 0,

    color = color
)


fun Habit.toUiState(): HabitEntity = HabitEntity(
    id = id,
    name = name,
    description = description,

    priority = priority.priorityName,
    type = type.typeName,

    frequency = frequency,
    repeatedTimes = repeatedTimes.toString(),
    quantity = quantity.toString(),

    color = color
)