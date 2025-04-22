package com.example.habittracker.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.AppTheme
import com.example.domain.repository.ThemeRepository
import com.example.domain.usecase.GetThemeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val themeRepository: ThemeRepository,
    private val getThemeUseCase: GetThemeUseCase
) : ViewModel() {

    private val _themeState = MutableStateFlow(AppTheme.SYSTEM)
    val themeState: StateFlow<AppTheme> = _themeState.asStateFlow()

    init {
        viewModelScope.launch {
            val currentTheme = getThemeUseCase()
            _themeState.value = currentTheme
        }
    }

    fun setTheme(theme: AppTheme) {
        viewModelScope.launch {
            themeRepository.setTheme(theme)
            _themeState.value = theme
        }
    }
}