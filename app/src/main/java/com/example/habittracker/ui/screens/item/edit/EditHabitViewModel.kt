package com.example.habittracker.ui.screens.item.edit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.DeleteHabitUseCase
import com.example.domain.usecase.GetHabitByIdUseCase
import com.example.domain.usecase.InsertHabitUseCase
import com.example.habittracker.ui.screens.item.HabitEntity
import com.example.habittracker.ui.screens.item.toHabit
import com.example.habittracker.ui.screens.item.toUiState
import com.example.habittracker.ui.screens.item.HabitItemState
import com.example.habittracker.ui.screens.item.UpdateAction
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class EditHabitViewModel @Inject constructor(
    private val getHabitByIdUseCase: GetHabitByIdUseCase,
    private val insertHabitUseCase: InsertHabitUseCase,
    private val deleteHabitUseCase: DeleteHabitUseCase
) : ViewModel() {
    var entryUiState by mutableStateOf(HabitItemState())
        private set

    fun loadHabitById(id: Int) {
        viewModelScope.launch {
            entryUiState = getHabitByIdUseCase(id)
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

    private fun validateInput(uiEntry: HabitEntity = entryUiState.currentHabit) = with(uiEntry) {
        name.isNotBlank() && description.isNotBlank() && canParseInt(uiEntry.repeatedTimes)
    }

    private fun canParseInt(repeatedTimes: String): Boolean =
        repeatedTimes.toIntOrNull() != null

    suspend fun updateItem() {
        if (validateInput()) {
            insertHabitUseCase(
                habit = entryUiState.currentHabit.toHabit()
            )
        }
    }

    suspend fun deleteItem() {
        deleteHabitUseCase(entryUiState.currentHabit.toHabit())
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