package com.example.habittracker.di

import com.example.domain.repository.LanguageRepository
import com.example.domain.usecase.GetAvailableLanguagesUseCase
import com.example.domain.usecase.GetCurrentLanguageUseCase
import com.example.domain.usecase.SetAppLanguageUseCase
import dagger.Module
import dagger.Provides

@Module
class TestUseCaseModule {

    @Provides
    fun provideGetCurrentLanguageUseCase(
        languageRepository: LanguageRepository
    ): GetCurrentLanguageUseCase {
        return GetCurrentLanguageUseCase(languageRepository)
    }

    @Provides
    fun provideGetAvailableLanguagesUseCase(
        languageRepository: LanguageRepository
    ): GetAvailableLanguagesUseCase {
        return GetAvailableLanguagesUseCase(languageRepository)
    }

    @Provides
    fun provideSetAppLanguageUseCase(
        languageRepository: LanguageRepository
    ): SetAppLanguageUseCase {
        return SetAppLanguageUseCase(languageRepository)
    }

}