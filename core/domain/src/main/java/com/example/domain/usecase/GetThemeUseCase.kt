package com.example.domain.usecase

import com.example.domain.model.AppTheme
import com.example.domain.repository.ThemeRepository
import javax.inject.Inject

class GetThemeUseCase @Inject constructor(
    private val themeRepository: ThemeRepository
) {
    suspend operator fun invoke(): AppTheme {
        return themeRepository.getTheme()
    }
}