package com.example.habittracker.di

import android.content.Context
import androidx.room.RoomDatabase
import com.example.data.local.OfflineDatabase
import com.example.domain.repository.LocalDataSource
import com.example.domain.repository.RemoteDataSource
import com.example.domain.usecase.DecreaseHabitQuantityUseCase
import com.example.domain.usecase.DeleteHabitByIdUseCase
import com.example.domain.usecase.DeleteHabitUseCase
import com.example.domain.usecase.GetAllHabitsUseCase
import com.example.domain.usecase.IncreaseHabitQuantityUseCase
import com.example.domain.usecase.InsertHabitUseCase
import com.example.domain.usecase.MarkHabitDoneUseCase
import com.example.domain.usecase.SyncFromRemoteToLocalUseCase
import com.example.domain.usecase.SyncLocalToRemoteUseCase
import dagger.Module
import dagger.Provides

@Module
class HabitsModule {

    @Provides
    fun provideOfflineDatabase(context: Context): RoomDatabase =
        OfflineDatabase.getDatabase(context)

    @Provides
    fun provideHabitDao(database: OfflineDatabase) = database.habitDao()

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
    fun provideGetAllHabitsUseCase(
        localDataSource: LocalDataSource
    ): GetAllHabitsUseCase {
        return GetAllHabitsUseCase(localDataSource)
    }

    @Provides
    fun provideMarkHabitDoneUseCase(
        remoteDataSource: RemoteDataSource
    ): MarkHabitDoneUseCase {
        return MarkHabitDoneUseCase(remoteDataSource)
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