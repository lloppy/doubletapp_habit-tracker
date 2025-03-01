package com.example.habittracker.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habittracker.data.FakeRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HabitTrackerViewModel : ViewModel() {
    var uiState: HabitTrackerState by mutableStateOf(HabitTrackerState.Loading)
        private set

    init {
        getHabits()
    }

    fun getHabits(){
        uiState = HabitTrackerState.Loading
        viewModelScope.launch {
            delay(DELAY_FOR_IMITATE_LOADING)
            val listResult = FakeRepository.getHabits()
            uiState = HabitTrackerState.Success(listResult)
        }

    }

    fun onClickHabit() {

    }

    companion object {
        const val DELAY_FOR_IMITATE_LOADING = 5000L
    }
}