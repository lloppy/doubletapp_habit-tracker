package com.example.habittracker.di

import android.content.Context
import com.example.domain.repository.LanguageRepository
import com.example.domain.repository.ThemeRepository
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        HabitsModule::class,
        NetworkModule::class,
        ContextModule::class,
        DatabaseModule::class
    ]
)
interface ApplicationComponent {
    fun context(): Context
    fun themeRepository(): ThemeRepository
    fun languageRepository(): LanguageRepository
}
