package com.example.habittracker.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habittracker.data.FakeRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HabitTrackerViewModel(
    private val repository: FakeRepository = FakeRepository()
): ViewModel() {
    var uiState: HabitTrackerState by mutableStateOf(HabitTrackerState.Loading)
        private set

    init {
        getHabits()
    }

    fun getHabits(){
        uiState = HabitTrackerState.Loading
        viewModelScope.launch {
            delay(DELAY_FOR_IMITATE_LOADING)
            val listResult = repository.getHabits()
            uiState = HabitTrackerState.Success(listResult)
        }

    }


    fun getDetailsFor(habitName: String) =
         repository.getSingleHabit(habitName)


    companion object {
        const val DELAY_FOR_IMITATE_LOADING = 3000L
    }
}