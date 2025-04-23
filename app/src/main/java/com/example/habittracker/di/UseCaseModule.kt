package com.example.habittracker.di

import com.example.domain.repository.LanguageRepository
import com.example.domain.repository.LocalDataSource
import com.example.domain.repository.RemoteDataSource
import com.example.domain.repository.ThemeRepository
import com.example.domain.usecase.DecreaseHabitQuantityUseCase
import com.example.domain.usecase.DeleteHabitByIdUseCase
import com.example.domain.usecase.DeleteHabitUseCase
import com.example.domain.usecase.GetAllHabitsUseCase
import com.example.domain.usecase.GetAvailableLanguagesUseCase
import com.example.domain.usecase.GetCurrentLanguageUseCase
import com.example.domain.usecase.GetHabitByIdUseCase
import com.example.domain.usecase.GetThemeUseCase
import com.example.domain.usecase.IncreaseHabitQuantityUseCase
import com.example.domain.usecase.InsertHabitUseCase
import com.example.domain.usecase.MarkHabitDoneUseCase
import com.example.domain.usecase.SetAppLanguageUseCase
import com.example.domain.usecase.SetThemeUseCase
import com.example.domain.usecase.SyncFromRemoteToLocalUseCase
import com.example.domain.usecase.SyncLocalToRemoteUseCase
import dagger.Module
import dagger.Provides

@Module
class UseCaseModule {

    @Provides
    fun provideGetCurrentLanguageUseCase(
        languageRepository: LanguageRepository
    ): GetCurrentLanguageUseCase {
        return GetCurrentLanguageUseCase(languageRepository)
    }

    @Provides
    fun provideGetAvailableLanguagesUseCase(
        languageRepository: LanguageRepository
    ): GetAvailableLanguagesUseCase {
        return GetAvailableLanguagesUseCase(languageRepository)
    }

    @Provides
    fun provideSetAppLanguageUseCase(
        languageRepository: LanguageRepository
    ): SetAppLanguageUseCase {
        return SetAppLanguageUseCase(languageRepository)
    }

    @Provides
    fun provideGetThemeUseCase(
        themeRepository: ThemeRepository
    ): GetThemeUseCase {
        return GetThemeUseCase(themeRepository)
    }

    @Provides
    fun provideSetThemeUseCase(
        themeRepository: ThemeRepository
    ): SetThemeUseCase {
        return SetThemeUseCase(themeRepository)
    }

    @Provides
    fun provideDeleteHabitUseCase(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ): DeleteHabitUseCase {
        return DeleteHabitUseCase(localDataSource, remoteDataSource)
    }

    @Provides
    fun provideDeleteHabitByIdUseCase(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ): DeleteHabitByIdUseCase {
        return DeleteHabitByIdUseCase(localDataSource, remoteDataSource)
    }

    @Provides
    fun provideIncreaseHabitQuantityUseCase(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ): IncreaseHabitQuantityUseCase {
        return IncreaseHabitQuantityUseCase(localDataSource, remoteDataSource)
    }

    @Provides
    fun provideDecreaseHabitQuantityUseCase(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ): DecreaseHabitQuantityUseCase {
        return DecreaseHabitQuantityUseCase(localDataSource, remoteDataSource)
    }

    @Provides
    fun provideInsertHabitUseCase(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ): InsertHabitUseCase {
        return InsertHabitUseCase(localDataSource, remoteDataSource)
    }

    @Provides
    fun provideGetHabitByIdUseCase(
        localDataSource: LocalDataSource
    ): GetHabitByIdUseCase {
        return GetHabitByIdUseCase(localDataSource)
    }

    @Provides
    fun provideGetAllHabitsUseCase(
        localDataSource: LocalDataSource
    ): GetAllHabitsUseCase {
        return GetAllHabitsUseCase(localDataSource)
    }

    @Provides
    fun provideMarkHabitDoneUseCase(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ): MarkHabitDoneUseCase {
        return MarkHabitDoneUseCase(localDataSource, remoteDataSource)
    }

    @Provides
    fun provideSyncFromRemoteToLocalUseCase(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ): SyncFromRemoteToLocalUseCase {
        return SyncFromRemoteToLocalUseCase(localDataSource, remoteDataSource)
    }

    @Provides
    fun provideSyncLocalToRemoteUseCase(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ): SyncLocalToRemoteUseCase {
        return SyncLocalToRemoteUseCase(localDataSource, remoteDataSource)
    }

}