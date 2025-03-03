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

data class HabitEntryState(
    val currentHabit: HabitDetails = HabitDetails(),
    val isEntryValid: Boolean = false,
)

class HabitEntryViewModel(
    private val repository: FakeRepository
) : ViewModel() {

    var entryUiState by mutableStateOf(HabitEntryState())
        private set

    fun updateUiState(newHabit: HabitDetails) {
        entryUiState = HabitEntryState(
            currentHabit = newHabit,
            isEntryValid = validateInput(newHabit)
        )
    }

    private fun validateInput(uiState: HabitDetails = entryUiState.currentHabit): Boolean {
        return with(uiState) {
            name.isNotBlank()
                    && description.isNotBlank()
                    && type.isNotBlank()
                    && canParseInt(uiState.repeatedTimes)
        }
    }

    private fun canParseInt(repeatedTimes: String): Boolean {
        return repeatedTimes.toIntOrNull() != null
    }


    fun saveItem() {
        if (validateInput()) {
            repository.insert(habit = entryUiState.currentHabit.toHabit())
        }
    }
}

data class HabitDetails(
    val name: String = "",
    val description: String = "",
    val priority: String = "",
    val type: String = "",
    val frequency: String = "",
    val repeatedTimes: String = "",
    val color: Color = Color.Green,
)

fun HabitDetails.toHabit(): Habit = Habit(
    name = name,
    description = description,
    priority = HabitPriority.entries.first{it.priorityName == priority},
    type = HabitType.entries.first{it.typeName == type},
    periodicity = HabitPeriodicity(frequency = frequency, repeatedTimes = repeatedTimes.toInt()),
    color = color //TODO()
)


fun Habit.toUiState(): HabitDetails = HabitDetails(
    name = name,
    description = description,
    priority = priority.priorityName,
    type = type.typeName,
    frequency = periodicity.frequency,
    repeatedTimes = periodicity.repeatedTimes.toString(),
    color = color //TODO()
)