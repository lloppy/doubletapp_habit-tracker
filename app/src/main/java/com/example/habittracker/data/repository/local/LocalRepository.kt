package com.example.habittracker.data.repository.local

import com.example.habittracker.model.domain.Habit
import kotlinx.coroutines.flow.Flow

interface LocalRepository {

    suspend fun insertHabit(habit: Habit): Result<Unit>

    suspend fun updateHabit(habit: Habit): Result<Unit>

    suspend fun deleteHabit(habit: Habit): Result<Unit>

    suspend fun deleteByHabitId(id: Int)

    suspend fun deleteAllHabits()

    suspend fun increaseHabitQuantity(id: Int)

    suspend fun decreaseHabitQuantity(id: Int)

    suspend fun getAllHabitsOnce(): List<Habit>

    fun getAllHabits(): Flow<List<Habit>>

    fun getHabitById(id: Int): Flow<Habit?>

}
