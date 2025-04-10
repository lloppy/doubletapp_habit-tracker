package com.example.habittracker.ui.screens.item.create

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.habittracker.data.repository.HabitsRepository
import com.example.habittracker.model.Habit
import com.example.habittracker.model.HabitCategory
import com.example.habittracker.model.HabitPriority
import com.example.habittracker.model.HabitType
import com.example.habittracker.ui.screens.item.HabitItemState
import com.example.habittracker.ui.screens.item.UpdateAction


class CreateHabitViewModel(
    private val habitsRepository: HabitsRepository
) : ViewModel() {

    var entryUiState by mutableStateOf(HabitItemState())
        private set

    private fun validateInput(uiEntry: HabitEntity = entryUiState.currentHabit): Boolean =
        with(uiEntry) {
            name.isNotBlank() && canParseInt(uiEntry.repeatedTimes)
        }


    private fun canParseInt(repeatedTimes: String): Boolean =
        with(repeatedTimes) {
            toIntOrNull() != null || isBlank()
        }


    suspend fun saveItem() {
        if (validateInput()) {
            habitsRepository.insertHabit(
                habit = entryUiState.currentHabit.toHabit()
            )
        }
    }

    fun handleAction(action: UpdateAction) {
        entryUiState = with(entryUiState) {
            when (action) {
                is UpdateAction.Name -> {
                    val updatedHabit = currentHabit.copy(name = action.name)
                    copy(currentHabit = updatedHabit, isEntryValid = validateInput(updatedHabit))
                }

                is UpdateAction.Frequency -> copy(currentHabit = currentHabit.copy(frequency = action.frequency))

                is UpdateAction.Description -> copy(currentHabit = currentHabit.copy(description = action.description))

                is UpdateAction.Category -> copy(currentHabit = currentHabit.copy(category = action.category))

                is UpdateAction.Color -> copy(currentHabit = currentHabit.copy(color = action.color))

                is UpdateAction.Priority -> copy(currentHabit = currentHabit.copy(priority = action.priority))

                is UpdateAction.Type -> copy(currentHabit = currentHabit.copy(type = action.type))

                is UpdateAction.RepeatedTimes -> {
                    val updatedHabit = currentHabit.copy(repeatedTimes = action.times)
                    copy(currentHabit = updatedHabit, isEntryValid = validateInput(updatedHabit))
                }
            }
        }
    }
}

data class HabitEntity(
    val id: Int = 0,
    val name: String = "",
    val description: String = "",
    val type: HabitType = HabitType.POSITIVE,
    val category: HabitCategory = HabitCategory.PRODUCTIVITY,
    val priority: HabitPriority = HabitPriority.MEDIUM,
    val frequency: String = "",
    val repeatedTimes: String = "",
    val quantity: String = "",
    val color: Color = Color.Yellow
)

fun HabitEntity.toHabit(): Habit = Habit(
    id = id,
    name = name,
    description = description,
    priority = priority,
    category = category,
    type = type,
    frequency = frequency,
    repeatedTimes = repeatedTimes.toIntOrNull() ?: 1,
    quantity = quantity.toIntOrNull() ?: 0,
    color = color
)


fun Habit.toUiState(): HabitEntity = HabitEntity(
    id = id,
    name = name,
    description = description,
    priority = priority,
    category = category,
    type = type,
    frequency = frequency,
    repeatedTimes = repeatedTimes.toString(),
    quantity = quantity.toString(),
    color = color
)