package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.model.domain.Habit
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(habitEntity: Habit)

    @Update
    suspend fun update(habitEntity: Habit)

    @Delete
    suspend fun delete(habitEntity: Habit)

    @Query("DELETE FROM habits WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("DELETE FROM habits")
    suspend fun deleteAll()

//    @Query("UPDATE habits SET quantity = quantity + 1 WHERE id = :id AND quantity < repeated_times")
//    suspend fun increaseQuantity(id: Int)
//
//    @Query("UPDATE habits SET quantity = quantity - 1 WHERE id = :id AND quantity > 0")
//    suspend fun decreaseQuantity(id: Int)

    @Query("SELECT * FROM habits WHERE id = :id")
    fun getById(id: Int): Flow<Habit>

    @Query("SELECT * FROM habits")
    fun getAll(): Flow<List<Habit>>

}