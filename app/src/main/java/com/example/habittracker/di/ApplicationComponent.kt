package com.example.habittracker.di

import android.content.Context
import com.example.domain.repository.LanguageRepository
import com.example.domain.repository.ThemeRepository
import com.example.habittracker.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        UseCaseModule::class,
        NetworkModule::class,
        ContextModule::class,
        DatabaseModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent {
    fun context(): Context
    fun themeRepository(): ThemeRepository
    fun languageRepository(): LanguageRepository

    fun inject(activity: MainActivity)
}
