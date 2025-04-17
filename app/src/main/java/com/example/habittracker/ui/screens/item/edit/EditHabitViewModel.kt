package com.example.habittracker.ui.screens.item.edit


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habittracker.data.repository.local.HabitsRepository
import com.example.habittracker.model.ui.HabitEntity
import com.example.habittracker.model.ui.toHabit
import com.example.habittracker.model.ui.toUiState
import com.example.habittracker.ui.screens.item.HabitItemState
import com.example.habittracker.ui.screens.item.UpdateAction
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class EditHabitViewModel(
    savedStateHandle: SavedStateHandle,
    private val habitsRepository: HabitsRepository
) : ViewModel() {
    private val stringId: String = checkNotNull(savedStateHandle[EditHabitDestination.itemIdArg])

    var entryUiState = MutableLiveData<HabitItemState>()
        private set

    init {
        viewModelScope.launch {
            habitsRepository.getHabitById(id = checkNotNull(stringId.toIntOrNull()))
                .filterNotNull()
                .map {
                    HabitItemState(
                        currentHabit = it.toUiState(),
                        isEntryValid = true
                    )
                }
                .first()
                .let { state ->
                    entryUiState.postValue(state)
                }
        }
    }

    private fun validateInput(
        uiEntry: HabitEntity = entryUiState.value?.currentHabit ?: HabitEntity()
    ): Boolean = with(uiEntry) {
        name.isNotBlank() && description.isNotBlank() && canParseInt(uiEntry.repeatedTimes)
    }


    private fun canParseInt(repeatedTimes: String): Boolean =
        repeatedTimes.toIntOrNull() != null


    suspend fun updateItem() {
        if (validateInput()) {
            entryUiState.value?.let { state ->
                habitsRepository.updateHabit(
                    habit = state.currentHabit.toHabit()
                )
            }
        }
    }

    suspend fun deleteItem() {
        entryUiState.value?.let { state ->
            habitsRepository.deleteHabit(state.currentHabit.toHabit())
        }
    }

    fun handleAction(action: UpdateAction) {
        entryUiState.value?.let { currentState ->
            val newState = when (action) {
                is UpdateAction.Name -> {
                    val updatedHabit = currentState.currentHabit.copy(name = action.name)
                    currentState.copy(
                        currentHabit = updatedHabit,
                        isEntryValid = validateInput(updatedHabit)
                    )
                }

                is UpdateAction.Frequency -> {
                    currentState.copy(
                        currentHabit = currentState.currentHabit.copy(frequency = action.frequency)
                    )
                }

                is UpdateAction.Description -> {
                    currentState.copy(
                        currentHabit = currentState.currentHabit.copy(description = action.description)
                    )
                }

                is UpdateAction.Category -> {
                    currentState.copy(
                        currentHabit = currentState.currentHabit.copy(category = action.category)
                    )
                }

                is UpdateAction.Color -> {
                    currentState.copy(
                        currentHabit = currentState.currentHabit.copy(color = action.color)
                    )
                }

                is UpdateAction.Priority -> {
                    currentState.copy(
                        currentHabit = currentState.currentHabit.copy(priority = action.priority)
                    )
                }

                is UpdateAction.Type -> {
                    currentState.copy(
                        currentHabit = currentState.currentHabit.copy(type = action.type)
                    )
                }

                is UpdateAction.RepeatedTimes -> {
                    val updatedHabit = currentState.currentHabit.copy(repeatedTimes = action.times)
                    currentState.copy(
                        currentHabit = updatedHabit,
                        isEntryValid = validateInput(updatedHabit)
                    )
                }
            }
            entryUiState.postValue(newState)
        }
    }
}