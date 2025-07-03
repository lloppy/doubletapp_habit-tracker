package com.example.domain.usecase

import com.example.domain.repository.LanguageRepository
import com.example.model.Language
import javax.inject.Inject

class GetAvailableLanguagesUseCase @Inject constructor(
    private val languageRepository: LanguageRepository
) {
    operator fun invoke(): List<Language> = languageRepository.getAvailableLanguages()
}
