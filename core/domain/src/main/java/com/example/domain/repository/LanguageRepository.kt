package com.example.domain.repository

import org.intellij.lang.annotations.Language

interface LanguageRepository {
    fun getCurrentLanguage(): String
    fun setAppLanguage(languageCode: String): Result<Unit>
    fun getAvailableLanguages(): List<Language>
}