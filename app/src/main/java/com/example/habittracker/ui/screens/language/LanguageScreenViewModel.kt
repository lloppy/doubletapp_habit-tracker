package com.example.habittracker.ui.screens.language

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.habittracker.data.repository.LanguageRepository

class LanguageScreenViewModel(
    private val languageRepository: LanguageRepository,
) : ViewModel() {
    var state by mutableStateOf(LanguageScreenState())
        private set

    init {
        loadLanguages()
    }

    private fun loadLanguages() {
        state = state.copy(
            currentLanguage = languageRepository.getCurrentLanguage(),
            availableLanguages = languageRepository.getAvailableLanguages()
        )
    }

    fun onLanguageSelected(languageCode: String) {
        languageRepository.setAppLanguage(languageCode)
            .onSuccess {
                state = state.copy(currentLanguage = languageCode)
            }
            .onFailure { e ->
                state = state.copy(error = e.localizedMessage)
            }
    }
}