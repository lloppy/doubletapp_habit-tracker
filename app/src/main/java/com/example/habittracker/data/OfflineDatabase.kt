package com.example.habittracker.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.local.dao.HabitDao

@Database(entities = [com.example.model.domain.Habit::class], version = 10, exportSchema = false)
@TypeConverters(com.example.model.domain.Converters::class)
abstract class OfflineDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDao

    companion object {
        @Volatile
        private var INSTANCE: OfflineDatabase? = null

        fun getDatabase(context: Context): OfflineDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context = context,
                    klass = OfflineDatabase::class.java,
                    name = "habit_database"
                )
                    .addTypeConverter(com.example.model.domain.Converters())
                    .fallbackToDestructiveMigration()
                    .build()
                    .also {
                        INSTANCE = it
                    }
            }
        }
    }
}