package com.example.habittracker.di

import android.content.Context
import com.example.data.local.OfflineDatabase
import com.example.data.local.dao.HabitDao
import com.example.data.local.datasource.HabitsLocalDataSource
import com.example.data.remote.api.HabitsApiService
import com.example.data.remote.datasource.HabitsRemoteDataSource
import com.example.domain.repository.LocalDataSource
import com.example.domain.repository.RemoteDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideOfflineDatabase(context: Context): OfflineDatabase =
        OfflineDatabase.getDatabase(context)

    @Provides
    fun provideHabitDao(database: OfflineDatabase) = database.habitDao()

    @Provides
    fun provideLocalDataSource(habitDao: HabitDao): LocalDataSource =
        HabitsLocalDataSource(habitDao)


    @Provides
    fun provideRemoteDataSource(retrofitService: HabitsApiService): RemoteDataSource =
        HabitsRemoteDataSource(retrofitService)

}
