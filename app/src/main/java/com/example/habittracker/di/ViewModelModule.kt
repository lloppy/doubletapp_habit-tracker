package com.example.habittracker.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.habittracker.DaggerViewModelFactory
import com.example.habittracker.ui.screens.MainViewModel
import com.example.habittracker.ui.screens.home.HabitTrackerViewModel
import com.example.habittracker.ui.screens.item.create.CreateHabitViewModel
import com.example.habittracker.ui.screens.item.edit.EditHabitViewModel
import com.example.habittracker.ui.screens.language.LanguageScreenViewModel
import com.example.habittracker.ui.screens.sync.SyncScreenViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: DaggerViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(HabitTrackerViewModel::class)
    abstract fun bindHabitTrackerViewModel(viewModel: HabitTrackerViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HabitTrackerViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HabitTrackerViewModel::class)
    abstract fun bindEditHabitViewModel(viewModel: EditHabitViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HabitTrackerViewModel::class)
    abstract fun bindCreateHabitViewModel(viewModel: CreateHabitViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HabitTrackerViewModel::class)
    abstract fun bindLanguageScreenViewModel(viewModel: LanguageScreenViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HabitTrackerViewModel::class)
    abstract fun bindSyncScreenViewModel(viewModel: SyncScreenViewModel): ViewModel


}

@MapKey
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class ViewModelKey(val value: KClass<out ViewModel>)
