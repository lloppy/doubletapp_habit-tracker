package com.example.domain.usecase

import com.example.domain.repository.LanguageRepository
import javax.inject.Inject

class GetCurrentLanguageUseCase @Inject constructor(
    private val languageRepository: LanguageRepository
) {
    operator fun invoke(): String = languageRepository.getCurrentLanguage()
}
