package com.example.domain.repository

import com.example.domain.util.DataError
import com.example.domain.util.EmptyResult
import com.example.model.Habit
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    suspend fun insertHabit(habit: Habit): EmptyResult<DataError.Local>

    suspend fun deleteHabit(habit: Habit)

    suspend fun deleteHabitById(id: Int)

    suspend fun deleteAllHabits()

    fun getHabitById(id: Int): Flow<Habit?>

    fun getAllHabits(): Flow<List<Habit>>

}
