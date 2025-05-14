package com.example.habittracker

import android.app.Application
import com.example.habittracker.di.DaggerTestApplicationComponent
import com.example.habittracker.di.TestApplicationComponent
import com.example.habittracker.di.TestContextModule
import com.example.habittracker.di.TestDatabaseModule
import com.example.habittracker.di.TestNetworkModule
import com.example.habittracker.di.TestUseCaseModule

class TestHabitTrackerApplication : Application() {
    lateinit var component: TestApplicationComponent

    override fun onCreate() {
        super.onCreate()

        component = DaggerTestApplicationComponent.builder()
            .testContextModule(TestContextModule(this))
            .testDatabaseModule(TestDatabaseModule())
            .testNetworkModule(TestNetworkModule())
            .testUseCaseModule(TestUseCaseModule())
            .build()
    }
}