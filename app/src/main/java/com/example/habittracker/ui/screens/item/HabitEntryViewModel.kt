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

    private fun validateInput(uiState: HabitEntity = entryUiState.currentHabit): Boolean {
        return with(uiState) {
            name.isNotBlank() && type.isNotBlank() && canParseInt(uiState.repeatedTimes)
        }
    }

    private fun canParseInt(repeatedTimes: String): Boolean =
        repeatedTimes.toIntOrNull() != null || repeatedTimes.isBlank()


    suspend fun saveItem() {
        if (validateInput()) {
            repository.insert(habit = entryUiState.currentHabit.toHabit())
        }
    }

    companion object {
        val priorityMap = HabitPriority.entries.associateBy { it.priorityName }
        val typeMap = HabitType.entries.associateBy { it.typeName }
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

fun HabitEntity.toHabit(
    defaultPriority: HabitPriority = HabitPriority.MEDIUM,
    defaultType: HabitType = HabitType.PRODUCTIVITY
): Habit = Habit(
    id = id,
    name = name,
    description = description,

    priority = HabitEntryViewModel.priorityMap[priority] ?: defaultPriority,
    type = HabitEntryViewModel.typeMap[type] ?: defaultType,

    frequency = frequency,
    repeatedTimes = repeatedTimes.toIntOrNull() ?: run {
        println("Warning: Invalid repeatedTimes '$repeatedTimes', using default 1")
        1
    },
    quantity = quantity.toIntOrNull() ?: run {
        println("Warning: Invalid quantity '$quantity', using default 0")
        0
    },

    color = color
)

fun HabitPriority.toEntityString() = this.priorityName
fun String.toHabitPriority() = HabitEntryViewModel.priorityMap[this] ?: HabitPriority.MEDIUM

fun HabitType.toEntityString() = this.typeName
fun String.toHabitType() = HabitEntryViewModel.typeMap[this] ?: HabitType.PRODUCTIVITY

fun Habit.toUiState(): HabitEntity = HabitEntity(
    id = id,
    name = name,
    description = description,

    priority = priority.toEntityString(),
    type = type.toEntityString(),

    frequency = frequency,
    repeatedTimes = repeatedTimes.toString(),
    quantity = quantity.toString(),

    color = color
)
