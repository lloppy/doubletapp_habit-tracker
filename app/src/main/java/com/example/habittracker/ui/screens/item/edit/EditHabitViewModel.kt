package com.example.habittracker.ui.screens.item.edit


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habittracker.data.repository.HabitsRepository
import com.example.habittracker.ui.screens.item.HabitItemState
import com.example.habittracker.ui.screens.item.UpdateAction
import com.example.habittracker.ui.screens.item.create.HabitEntity
import com.example.habittracker.ui.screens.item.create.toHabit
import com.example.habittracker.ui.screens.item.create.toUiState
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class EditHabitViewModel(
    savedStateHandle: SavedStateHandle,
    private val habitsRepository: HabitsRepository
) : ViewModel() {
    private val stringId: String = checkNotNull(savedStateHandle[EditHabitDestination.itemIdArg])

    var entryUiState by mutableStateOf(HabitItemState())
        private set

    init {
        viewModelScope.launch {
            entryUiState = habitsRepository.getHabitById(id = checkNotNull(stringId.toIntOrNull()))
                .filterNotNull()
                .map {
                    HabitItemState(
                        currentHabit = it.toUiState(),
                        isEntryValid = true
                    )
                }
                .first()
        }
    }

    private fun validateInput(uiEntry: HabitEntity = entryUiState.currentHabit): Boolean =
        with(uiEntry) {
            name.isNotBlank() && canParseInt(uiEntry.repeatedTimes)
        }


    private fun canParseInt(repeatedTimes: String): Boolean =
        repeatedTimes.toIntOrNull() != null


    suspend fun updateItem() {
        if (validateInput()) {
            habitsRepository.updateHabit(
                habit = entryUiState.currentHabit.toHabit()
            )
        }
    }

    suspend fun deleteItem() {
        habitsRepository.deleteHabit(entryUiState.currentHabit.toHabit())
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
