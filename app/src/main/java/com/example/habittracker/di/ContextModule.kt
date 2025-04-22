package com.example.habittracker.di

import android.content.Context
import com.example.data.repository.LanguageRepositoryImpl
import com.example.data.repository.ThemeRepositoryImpl
import com.example.domain.repository.LanguageRepository
import com.example.domain.repository.ThemeRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ContextModule(private val context: Context) {

    @Provides
    fun provideContext() = context

    @Provides
    @Singleton
    fun provideThemeRepository(context: Context): ThemeRepository =
        ThemeRepositoryImpl(context)

    @Provides
    @Singleton
    fun provideLanguageRepository(context: Context): LanguageRepository =
        LanguageRepositoryImpl(context)

}
