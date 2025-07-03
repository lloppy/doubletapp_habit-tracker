package com.example.habittracker.di

import androidx.lifecycle.ViewModelProvider
import com.example.habittracker.ui.screens.language.LanguageScreenTest
import com.example.habittracker.ui.screens.language.LanguageScreenViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        TestUseCaseModule::class,
        TestContextModule::class,
        TestNetworkModule::class,
        TestDatabaseModule::class,
        ViewModelModule::class
    ]
)
interface TestApplicationComponent : ApplicationComponent {
    fun inject(test: LanguageScreenTest)
    fun getViewModelFactory(): ViewModelProvider.Factory
    fun getLanguageViewModel(): LanguageScreenViewModel
}