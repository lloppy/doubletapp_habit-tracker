package com.example.habittracker.data.repository.local

import com.example.habittracker.model.domain.Habit
import com.example.habittracker.model.model.ApiResult
import kotlinx.coroutines.flow.Flow

interface LocalRepository {

    suspend fun insertHabit(habit: Habit): Result<Unit>

    suspend fun deleteHabit(habit: Habit): Result<Unit>

    suspend fun deleteByHabitId(id: Int)

    suspend fun deleteAllHabits()

    suspend fun increaseHabitQuantity(id: Int): ApiResult<Unit>

    suspend fun decreaseHabitQuantity(id: Int)

    suspend fun getHabitAtOnce(id: Int): Habit

    fun getAllHabits(): Flow<List<Habit>>

    fun getHabitById(id: Int): Flow<Habit?>

}
