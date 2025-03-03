package com.example.habittracker.ui.screens.item


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habittracker.data.FakeRepository
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class EditHabitViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: FakeRepository
) : ViewModel() {
    private val habitName: String = checkNotNull(savedStateHandle[EditHabitDestination.itemIdArg])

    var entryUiState by mutableStateOf(HabitEntryState())
        private set

    fun updateUiState(newHabit: HabitDetails) {
        entryUiState = HabitEntryState(
            currentHabit = newHabit,
            isEntryValid = validateInput(newHabit)
        )
    }

    init {
        viewModelScope.launch {
            entryUiState = repository.getSingleHabit(habitName)
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

    fun updateItem(){
        if (validateInput()) {
            repository.updateItem(habit = entryUiState.currentHabit.toHabit())
        }
    }

}
