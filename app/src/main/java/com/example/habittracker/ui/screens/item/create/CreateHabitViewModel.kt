package com.example.habittracker.ui.screens.item.create

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.habittracker.data.repository.local.HabitsRepository
import com.example.habittracker.model.ui.HabitEntity
import com.example.habittracker.model.ui.toHabit
import com.example.habittracker.ui.screens.item.HabitItemState
import com.example.habittracker.ui.screens.item.UpdateAction

class CreateHabitViewModel(
    private val habitsRepository: HabitsRepository,
) : ViewModel() {

    var entryUiState by mutableStateOf(HabitItemState())
        private set

    private fun validateInput(uiEntry: HabitEntity = entryUiState.currentHabit): Boolean =
        with(uiEntry) {
            name.isNotBlank() && description.isNotBlank() && canParseInt(uiEntry.repeatedTimes)
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
        entryUiState = when (action) {
            is UpdateAction.Name -> {
                entryUiState.copy(
                    currentHabit = entryUiState.currentHabit.copy(name = action.name),
                    isEntryValid = validateInput(entryUiState.currentHabit.copy(name = action.name))
                )
            }

            is UpdateAction.Frequency -> {
                entryUiState.copy(
                    currentHabit = entryUiState.currentHabit.copy(frequency = action.frequency)
                )
            }

            is UpdateAction.Description -> {
                entryUiState.copy(
                    currentHabit = entryUiState.currentHabit.copy(description = action.description)
                )
            }

            is UpdateAction.Category -> {
                entryUiState.copy(
                    currentHabit = entryUiState.currentHabit.copy(category = action.category)
                )
            }

            is UpdateAction.Color -> {
                entryUiState.copy(
                    currentHabit = entryUiState.currentHabit.copy(color = action.color)
                )
            }

            is UpdateAction.Priority -> {
                entryUiState.copy(
                    currentHabit = entryUiState.currentHabit.copy(priority = action.priority)
                )
            }

            is UpdateAction.Type -> {
                entryUiState.copy(
                    currentHabit = entryUiState.currentHabit.copy(type = action.type)
                )
            }

            is UpdateAction.RepeatedTimes -> {
                entryUiState.copy(
                    currentHabit = entryUiState.currentHabit.copy(repeatedTimes = action.times),
                    isEntryValid = validateInput(entryUiState.currentHabit.copy(repeatedTimes = action.times))
                )
            }
        }
    }
}
