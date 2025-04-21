package com.example.data.util

import com.example.habittracker.data.repository.LanguageRepository
import com.example.habittracker.model.Language

class LanguageRepositoryProxy(
    private val languageRepository: LanguageRepository
) : Logger("LanguageRepositoryProxy"), LanguageRepository {

    override fun getCurrentLanguage(): String {
        return languageRepository.getCurrentLanguage().also {
            log("Current language: $it")
        }
    }

    override fun setAppLanguage(languageCode: String): Result<Unit> {
        return languageRepository.setAppLanguage(languageCode)
            .onSuccess { log("Language successfully changed to: $languageCode") }
            .onFailure { logError("Failed to set language: $languageCode", it) }
    }

    override fun getAvailableLanguages(): List<Language> {
        return languageRepository.getAvailableLanguages().also {
            logInfo("Available languages: ${it.joinToString()}")
        }
    }
}