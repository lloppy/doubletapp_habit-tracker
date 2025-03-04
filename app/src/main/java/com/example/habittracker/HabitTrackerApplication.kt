package com.example.habittracker

import android.app.Application
import com.example.habittracker.data.AppContainer
import com.example.habittracker.data.AppDataContainer


class HabitTrackerApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()

        container = AppDataContainer(this)
    }
}