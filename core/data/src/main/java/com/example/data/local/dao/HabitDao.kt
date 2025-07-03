package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.local.entity.HabitEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(habitEntity: HabitEntity)

    @Delete
    suspend fun delete(habitEntity: HabitEntity)

    @Query("DELETE FROM habits WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("DELETE FROM habits")
    suspend fun deleteAll()

    @Query("SELECT * FROM habits WHERE id = :id")
    fun getById(id: Int): Flow<HabitEntity>

    @Query("SELECT * FROM habits")
    fun getAll(): Flow<List<HabitEntity>>

}