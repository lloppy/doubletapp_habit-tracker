package com.example.habittracker.ui.screens.sync

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habittracker.data.repository.local.HabitsRepository
import kotlinx.coroutines.launch

class SyncScreenViewModel(
    private val habitsRepository: HabitsRepository,
) : ViewModel() {
    var state by mutableStateOf<SyncScreenState>(SyncScreenState.Idle)
        private set


    fun syncFromRemoteToLocal() = viewModelScope.launch {
        state = SyncScreenState.Loading
        val result = habitsRepository.syncFromRemoteToLocal()
        state = result.fold(
            onSuccess = { SyncScreenState.Success("Sync from server completed") },
            onFailure = { SyncScreenState.Error(it.message ?: "Unknown error occurred") }
        )
    }

    fun syncFromLocalToRemote() = viewModelScope.launch {
        state = SyncScreenState.Loading
        val result = habitsRepository.syncFromLocalToRemote()
        state = result.fold(
            onSuccess = { SyncScreenState.Success("Sync to server completed") },
            onFailure = { SyncScreenState.Error(it.message ?: "Unknown error occurred") }
        )
    }

}