package com.example.habittracker.ui.screens.item

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.habittracker.data.FakeRepository
import com.example.habittracker.model.Habit
import com.example.habittracker.model.HabitPeriodicity
import com.example.habittracker.model.HabitPriority
import com.example.habittracker.model.HabitType

class HabitDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: FakeRepository = FakeRepository()
) : ViewModel() {
    var entryUiState by mutableStateOf(HabitEntryState())
        private set

    fun updateEntryState(newHabit: HabitDetails) {
        entryUiState = HabitEntryState(currentHabit = newHabit, validateInput(newHabit))
    }

    private fun validateInput(uiState: HabitDetails = entryUiState.currentHabit): Boolean {
        return with(uiState) {
            name.isNotBlank()
                    && description.isNotBlank()
                    && periodicity.isNotBlank()
        }
    }

    fun saveItem() {
        if (validateInput()) {
            repository.insert(habit = entryUiState.currentHabit.toHabit())
        }
    }

    fun getDetailsFor(habitName: String) =
        checkNotNull(repository.getSingleHabit(habitName))

}

data class HabitEntryState(
    val currentHabit: HabitDetails = HabitDetails(),
    val isEntryValid: Boolean = false
)

data class HabitDetails(
    val name: String = "",
    val description: String = "",
    val priority: String = "",
    val type: String = "",
    val periodicity: String = "",
    val color: Color = Color.Green,
)

fun HabitDetails.toHabit(): Habit = Habit(
    name = name,
    description = description,
    priority = HabitPriority.valueOf(priority),
    type = HabitType.valueOf(type),
    periodicity = HabitPeriodicity(
        frequency = periodicity,
        daysOfWeek = emptyList() //TODO()
    ),
    color = color //TODO()
)


fun Habit.toUiState(): HabitDetails = HabitDetails(
    name = name,
    description = description,
    priority = priority.name,
    type = type.name,
    periodicity = periodicity.frequency,
    color = color //TODO()
)