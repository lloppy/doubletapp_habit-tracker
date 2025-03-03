package com.example.habittracker.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habittracker.data.ThemeRepository
import com.example.habittracker.ui.theme.AppTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val themeRepository: ThemeRepository
) : ViewModel() {

    private val _themeState = MutableStateFlow(themeRepository.themeState.value)
    val themeState: StateFlow<AppTheme> = _themeState.asStateFlow()

    fun setTheme(theme: AppTheme) {
        viewModelScope.launch {
            themeRepository.setTheme(theme)
            _themeState.value = theme
        }
    }
}