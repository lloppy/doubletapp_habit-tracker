package com.example.domain.usecase

import com.example.domain.repository.LanguageRepository
import javax.inject.Inject

class SetAppLanguageUseCase @Inject constructor(
    private val languageRepository: LanguageRepository
) {
    operator fun invoke(code: String) = languageRepository.setAppLanguage(languageCode = code)
}
