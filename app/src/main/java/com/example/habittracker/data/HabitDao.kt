package com.example.habittracker.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.habittracker.model.Habit
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(habit: Habit)

    @Update
    suspend fun update(newHabit: Habit)

    @Delete
    suspend fun delete(habit: Habit)

    @Query("DELETE FROM habits WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("UPDATE habits SET quantity = quantity + 1 WHERE id = :habitId AND quantity < repeated_times")
    suspend fun increaseQuantity(habitId: Int)

    @Query("UPDATE habits SET quantity = quantity - 1 WHERE id = :habitId AND quantity > 0")
    suspend fun decreaseQuantity(habitId: Int)


    @Query("SELECT * FROM habits")
    fun getAllHabits(): Flow<List<Habit>>

    @Query("SELECT * FROM habits WHERE id = :id")
    fun getHabit(id: Int): Flow<Habit>

}