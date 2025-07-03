package com.example.habittracker.di

import android.content.Context
import androidx.room.Room
import com.example.data.local.OfflineDatabase
import com.example.data.local.dao.HabitDao
import com.example.data.local.datasource.HabitsLocalDataSource
import com.example.data.local.mappers.Converters
import com.example.data.remote.api.HabitsApiService
import com.example.data.remote.datasource.HabitsRemoteDataSource
import com.example.domain.repository.LocalDataSource
import com.example.domain.repository.RemoteDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TestDatabaseModule {

    @Provides
    @Singleton
    fun provideOfflineDatabase(context: Context): OfflineDatabase =
        Room.inMemoryDatabaseBuilder(
            context = context,
            klass = OfflineDatabase::class.java,
        )
            .addTypeConverter(Converters())
            .fallbackToDestructiveMigration()
            .build()


    @Provides
    fun provideHabitDao(database: OfflineDatabase) = database.habitDao()

    @Provides
    fun provideLocalDataSource(habitDao: HabitDao): LocalDataSource =
        HabitsLocalDataSource(habitDao)


    @Provides
    fun provideRemoteDataSource(retrofitService: HabitsApiService): RemoteDataSource =
        HabitsRemoteDataSource(retrofitService)

}
