package com.example.habittracker.ui.screens.sync

sealed class SyncScreenState {
    data class Success(val message: String) : SyncScreenState()
    object Loading : SyncScreenState()
    data class Error(val message: String) : SyncScreenState()
    object Idle : SyncScreenState()
}
