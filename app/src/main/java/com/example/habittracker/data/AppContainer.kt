package com.example.habittracker.data

import android.content.Context

interface AppContainer {
    val habitsRepository: HabitsRepository
    val themeRepository: ThemeRepository
}

class AppDataContainer(private val context: Context) : AppContainer {

    override val habitsRepository: HabitsRepository by lazy {
        OfflineHabitsRepository(
            habitDao = OfflineDatabase.getDatabase(context).habitDao()
        )
    }
    override val themeRepository: ThemeRepository = ThemeRepository(context)

}