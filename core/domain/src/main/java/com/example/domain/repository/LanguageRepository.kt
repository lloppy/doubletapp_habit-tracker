package com.example.domain.repository

import com.example.model.Language


interface LanguageRepository {
    fun getCurrentLanguage(): String
    fun setAppLanguage(languageCode: String): Result<Unit>
    fun getAvailableLanguages(): List<Language>
}