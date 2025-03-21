package com.example.habittracker.data

import com.example.habittracker.model.Habit
import kotlinx.coroutines.flow.Flow

interface HabitsRepository {

    suspend fun insertHabit(habit: Habit)

    suspend fun updateHabit(newHabit: Habit)

    suspend fun deleteHabit(habit: Habit)

    suspend fun deleteHabitById(id: Int)

    suspend fun increaseHabitQuantity(id: Int)

    suspend fun decreaseHabitQuantity(id: Int)

    fun getAllHabits(): Flow<List<Habit>>

    fun getHabitById(id: Int): Flow<Habit?>

}


class OfflineHabitsRepository(private val habitDao: HabitDao) : HabitsRepository {

    override suspend fun insertHabit(habit: Habit) =
        habitDao.insertHabit(habit)

    override suspend fun updateHabit(newHabit: Habit) =
        habitDao.updateHabit(newHabit)

    override suspend fun increaseHabitQuantity(id: Int) =
        habitDao.increaseHabitQuantity(id)

    override suspend fun decreaseHabitQuantity(id: Int) =
        habitDao.decreaseHabitQuantity(id)

    override suspend fun deleteHabit(habit: Habit) =
        habitDao.deleteHabit(habit)

    override suspend fun deleteHabitById(id: Int) =
        habitDao.deleteHabitById(id)

    override fun getAllHabits(): Flow<List<Habit>> =
        habitDao.getAllHabits()

    override fun getHabitById(id: Int): Flow<Habit?> =
        habitDao.getHabitById(id)
}
