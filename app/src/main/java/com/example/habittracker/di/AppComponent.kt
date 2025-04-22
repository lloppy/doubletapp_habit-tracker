package com.example.habittracker.di

import com.example.domain.repository.LanguageRepository
import com.example.domain.repository.ThemeRepository
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        HabitsModule::class,
        NetworkModule::class,
        ContextModule::class
    ]
)
interface ApplicationComponent {
    fun themeRepository(): ThemeRepository
    fun languageRepository(): LanguageRepository
}
