package com.example.habittracker.data.repository

import com.example.habittracker.data.HabitDao
import com.example.habittracker.model.Habit
import kotlinx.coroutines.flow.Flow

interface HabitsRepository {

    suspend fun insertHabit(habit: Habit)

    suspend fun updateHabit(habit: Habit)

    suspend fun deleteHabit(habit: Habit)

    suspend fun deleteByHabitId(id: Int)

    suspend fun increaseHabitQuantity(id: Int)

    suspend fun decreaseHabitQuantity(id: Int)

    fun getAllHabits(): Flow<List<Habit>>

    fun getHabitById(id: Int): Flow<Habit?>

}

class OfflineHabitsRepository(private val habitDao: HabitDao) : HabitsRepository {

    override suspend fun insertHabit(habit: Habit) =
        habitDao.insert(habit)

    override suspend fun updateHabit(habit: Habit) =
        habitDao.update(habit)

    override suspend fun increaseHabitQuantity(id: Int) =
        habitDao.increaseQuantity(id)

    override suspend fun decreaseHabitQuantity(id: Int) =
        habitDao.decreaseQuantity(id)

    override suspend fun deleteHabit(habit: Habit) =
        habitDao.delete(habit)

    override suspend fun deleteByHabitId(id: Int) =
        habitDao.deleteById(id)

    override fun getAllHabits(): Flow<List<Habit>> =
        habitDao.getAll()

    override fun getHabitById(id: Int): Flow<Habit?> =
        habitDao.getById(id)
}