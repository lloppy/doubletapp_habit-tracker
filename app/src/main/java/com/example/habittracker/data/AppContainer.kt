package com.example.habittracker.data

import android.content.Context
import com.example.habittracker.data.repository.ContextRepository
import com.example.habittracker.data.repository.ContextRepositoryImpl
import com.example.habittracker.data.repository.HabitsRepository
import com.example.habittracker.data.repository.LanguageRepository
import com.example.habittracker.data.repository.LanguageRepositoryImpl
import com.example.habittracker.data.repository.OfflineHabitsRepository
import com.example.habittracker.data.repository.ThemeRepository
import com.example.habittracker.data.repository.ThemeRepositoryImpl
import com.example.habittracker.data.util.LanguageRepositoryProxy

interface AppContainer {
    val habitsRepository: HabitsRepository
    val themeRepository: ThemeRepository
    val contextRepository: ContextRepository
    val languageRepository: LanguageRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val habitsRepository: HabitsRepository by lazy {
        OfflineHabitsRepository(habitDao = OfflineDatabase.getDatabase(context).habitDao())
    }

    override val themeRepository: ThemeRepository = ThemeRepositoryImpl(context)

    override val contextRepository: ContextRepository = ContextRepositoryImpl(context)

    override val languageRepository: LanguageRepository = LanguageRepositoryProxy(
        languageRepository = LanguageRepositoryImpl(context)
    )
}