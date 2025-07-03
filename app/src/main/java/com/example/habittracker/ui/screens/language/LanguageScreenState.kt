package com.example.habittracker.ui.screens.language

import com.example.model.Language

data class LanguageScreenState(
    val currentLanguage: String = "",
    val availableLanguages: List<Language> = emptyList(),
    val error: String? = null
)