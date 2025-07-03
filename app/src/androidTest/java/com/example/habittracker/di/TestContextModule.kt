package com.example.habittracker.di

import android.content.Context
import com.example.domain.repository.LanguageRepository
import com.example.domain.repository.ThemeRepository
import dagger.Module
import dagger.Provides
import org.mockito.Mockito.mock
import javax.inject.Singleton

@Module
class TestContextModule(private val context: Context) {
    @Provides
    fun provideContext() = context

    @Provides
    @Singleton
    fun provideThemeRepository(): ThemeRepository = mock(ThemeRepository::class.java)

    @Provides
    @Singleton
    fun provideLanguageRepository(): LanguageRepository = mock(LanguageRepository::class.java)
}