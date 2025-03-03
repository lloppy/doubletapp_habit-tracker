package com.example.habittracker.ui.screens.item

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.habittracker.data.FakeRepository
import com.example.habittracker.model.Habit
import com.example.habittracker.model.HabitPeriodicity
import com.example.habittracker.model.HabitPriority
import com.example.habittracker.model.HabitType
import java.util.UUID

data class HabitEntryState(
    val currentHabit: HabitEntity = HabitEntity(),
    val isEntryValid: Boolean = false,
)

class HabitEntryViewModel(
    private val repository: FakeRepository
) : ViewModel() {

    var entryUiState by mutableStateOf(HabitEntryState())
        private set

    fun updateUiState(newHabit: HabitEntity) {
        entryUiState = HabitEntryState(
            currentHabit = newHabit,
            isEntryValid = validateInput(newHabit)
        )
    }

    private fun validateInput(uiState: HabitEntity = entryUiState.currentHabit): Boolean {
        return with(uiState) {
            name.isNotBlank()
                    && type.isNotBlank()
                    && canParseInt(uiState.repeatedTimes)
        }
    }

    private fun canParseInt(repeatedTimes: String): Boolean {
        return repeatedTimes.toIntOrNull() != null || repeatedTimes.isBlank()
    }


    fun saveItem() {
        if (validateInput()) {
            repository.addHabit(habit = entryUiState.currentHabit.toHabit())
        }
    }
}

data class HabitEntity(
    val id: String = UUID.randomUUID().toString(),
    val name: String = "",
    val description: String = "",
    val priority: String = "",
    val type: String = "",
    val frequency: String = "",
    val repeatedTimes: String = "",
    val color: Color = Color.Green,
)

fun HabitEntity.toHabit(): Habit = Habit(
    id = id,
    name = name,
    description = description,
    priority = HabitPriority.entries.firstOrNull { it.priorityName == priority }
        ?: HabitPriority.MEDIUM,
    type = HabitType.entries.firstOrNull { it.typeName == type }
        ?: HabitType.PRODUCTIVITY,
    periodicity = HabitPeriodicity(
        frequency = frequency,
        repeatedTimes = repeatedTimes.toIntOrNull() ?: 1
    ),
    color = color //TODO()
)


fun Habit.toUiState(): HabitEntity = HabitEntity(
    id = id,
    name = name,
    description = description,
    priority = priority.priorityName,
    type = type.typeName,
    frequency = periodicity.frequency,
    repeatedTimes = periodicity.repeatedTimes.toString(),
    color = color //TODO()
)