package com.example.habittracker.ui.screens.sync

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.SyncFromRemoteToLocalUseCase
import com.example.domain.usecase.SyncLocalToRemoteUseCase
import com.example.domain.util.onError
import kotlinx.coroutines.launch
import javax.inject.Inject

class SyncScreenViewModel @Inject constructor(
    private val syncLocalToRemoteUseCase: SyncLocalToRemoteUseCase,
    private val syncFromRemoteToLocalUseCase: SyncFromRemoteToLocalUseCase
) : ViewModel() {
    var state by mutableStateOf<SyncScreenState>(SyncScreenState.Idle)
        private set


    fun syncFromRemoteToLocal() = viewModelScope.launch {
        state = SyncScreenState.Loading
        val result = syncFromRemoteToLocalUseCase()

        result.onError { error ->
            state = SyncScreenState.Error(error.toString())
        }
        if (state is SyncScreenState.Loading) {
            state = SyncScreenState.Success("Sync from server completed")
        }
    }

    fun syncFromLocalToRemote() = viewModelScope.launch {
        state = SyncScreenState.Loading
        val result = syncLocalToRemoteUseCase()

        result.onError { error ->
            state = SyncScreenState.Error(error.toString())
        }
        if (state is SyncScreenState.Loading) {
            state = SyncScreenState.Success("Sync to server completed")
        }
    }
}