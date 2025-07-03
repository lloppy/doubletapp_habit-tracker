package com.example.domain.usecase

import com.example.domain.repository.ThemeRepository
import com.example.model.AppTheme
import javax.inject.Inject

class SetThemeUseCase @Inject constructor(
    private val themeRepository: ThemeRepository
) {
    suspend operator fun invoke(theme: AppTheme) {
        return themeRepository.setTheme(theme = theme)
    }
}