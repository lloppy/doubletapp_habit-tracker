package com.example.habittracker.ui.screens.language

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.domain.usecase.GetAvailableLanguagesUseCase
import com.example.domain.usecase.GetCurrentLanguageUseCase
import com.example.domain.usecase.SetAppLanguageUseCase
import javax.inject.Inject

class LanguageScreenViewModel @Inject constructor(
    private val getCurrentLanguageUseCase: GetCurrentLanguageUseCase,
    private val getAvailableLanguagesUseCase: GetAvailableLanguagesUseCase,
    private val setAppLanguageUseCase: SetAppLanguageUseCase
) : ViewModel() {
    var state by mutableStateOf(LanguageScreenState())
        private set

    init {
        loadLanguages()
    }

    private fun loadLanguages() {
        state = state.copy(
            currentLanguage = getCurrentLanguageUseCase(),
            availableLanguages = getAvailableLanguagesUseCase()
        )
    }

    fun onLanguageSelected(languageCode: String) {
        setAppLanguageUseCase(languageCode)
            .onSuccess {
                state = state.copy(currentLanguage = languageCode)
            }
            .onFailure { e ->
                state = state.copy(error = e.localizedMessage)
            }
    }
}