package com.example.domain.repository

import com.example.model.domain.Habit
import com.example.model.model.ApiResult
import kotlinx.coroutines.flow.Flow

interface LocalRepository {

    suspend fun saveHabit(habit: Habit): Result<Unit>

    suspend fun deleteHabit(habit: Habit): Result<Unit>

    suspend fun deleteByHabitId(id: Int)

    suspend fun deleteAllHabits()

    suspend fun increaseHabitQuantity(id: Int): ApiResult<Unit>

    suspend fun decreaseHabitQuantity(id: Int)

    suspend fun getHabitAtOnce(id: Int): Habit

    fun getAllHabits(): Flow<List<Habit>>

    fun getHabitById(id: Int): Flow<Habit?>

}
