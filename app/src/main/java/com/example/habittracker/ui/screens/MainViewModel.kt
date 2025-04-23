package com.example.habittracker.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.GetThemeUseCase
import com.example.domain.usecase.SetThemeUseCase
import com.example.model.AppTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val setThemeUseCase: SetThemeUseCase,
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
            setThemeUseCase(theme)
            _themeState.value = theme
        }
    }
}