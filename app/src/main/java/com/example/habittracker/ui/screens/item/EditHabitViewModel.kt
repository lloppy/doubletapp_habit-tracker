package com.example.habittracker.ui.screens.item


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habittracker.data.HabitsRepository
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class EditHabitViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: HabitsRepository
) : ViewModel() {
    private val stringId: String = checkNotNull(savedStateHandle[EditHabitDestination.itemIdArg])

    var entryUiState by mutableStateOf(HabitEntryState())
        private set

    fun updateUiState(newHabit: HabitEntity) {
        entryUiState = HabitEntryState(
            currentHabit = newHabit,
            isEntryValid = validateInput(newHabit)
        )
    }

    init {
        viewModelScope.launch {
            entryUiState = repository.getHabit(checkNotNull(stringId.toIntOrNull()))
                .filterNotNull()
                .map {
                    HabitEntryState(
                        currentHabit = it.toUiState(),
                        isEntryValid = true
                    )
                }
                .first()
        }
    }

    private fun validateInput(uiState: HabitEntity = entryUiState.currentHabit): Boolean =
        with(uiState) {
            name.isNotBlank()
                    && category.isNotBlank()
                    && type.isNotBlank()
                    && canParseInt(uiState.repeatedTimes)
        }


    private fun canParseInt(repeatedTimes: String): Boolean =
        repeatedTimes.toIntOrNull() != null


    suspend fun updateItem() {
        if (validateInput()) {
            repository.update(newHabit = entryUiState.currentHabit.toHabit())
        }
    }

    suspend fun deleteItem() {
        repository.delete(entryUiState.currentHabit.toHabit())
    }
}
