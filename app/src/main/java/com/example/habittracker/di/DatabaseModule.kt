package com.example.habittracker.di

import android.content.Context
import androidx.room.RoomDatabase
import com.example.data.local.OfflineDatabase
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {

    @Provides
    fun provideOfflineDatabase(context: Context): RoomDatabase =
        OfflineDatabase.getDatabase(context)

    @Provides
    fun provideHabitDao(database: OfflineDatabase) = database.habitDao()

}
