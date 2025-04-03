package com.example.habittracker.ui.screens.item.edit


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habittracker.data.repository.ContextRepository
import com.example.habittracker.data.repository.HabitsRepository
import com.example.habittracker.ui.screens.item.HabitItemState
import com.example.habittracker.ui.screens.item.create.HabitEntity
import com.example.habittracker.ui.screens.item.create.toHabit
import com.example.habittracker.ui.screens.item.create.toUiState
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class EditHabitViewModel(
    savedStateHandle: SavedStateHandle,
    private val habitsRepository: HabitsRepository,
    private val contextRepository: ContextRepository
) : ViewModel() {
    private val stringId: String = checkNotNull(savedStateHandle[EditHabitDestination.itemIdArg])

    var entryUiState by mutableStateOf(HabitItemState())
        private set

    fun updateUiState(newHabit: HabitEntity) {
        entryUiState = HabitItemState(
            currentHabit = newHabit,
            isEntryValid = validateInput(uiEntry = newHabit)
        )
    }

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
            name.isNotBlank()
                    && contextRepository.getString(category).isNotBlank()
                    && contextRepository.getString(type).isNotBlank()
                    && canParseInt(uiEntry.repeatedTimes)
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
}
