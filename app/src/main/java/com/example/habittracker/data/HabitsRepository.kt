package com.example.habittracker.data

import com.example.habittracker.model.Habit
import kotlinx.coroutines.flow.Flow

interface HabitsRepository {

    suspend fun insert(habit: Habit)

    suspend fun update(newHabit: Habit)

    suspend fun delete(habit: Habit)

    suspend fun deleteById(id: Int)

    suspend fun increaseQuantity(id: Int)

    suspend fun decreaseQuantity(id: Int)

    fun getAllHabits(): Flow<List<Habit>>

    fun getHabit(id: Int): Flow<Habit?>

}


class OfflineHabitsRepository(private val habitDao: HabitDao) : HabitsRepository {

    override suspend fun insert(habit: Habit) =
        habitDao.insert(habit)

    override suspend fun update(newHabit: Habit) =
        habitDao.update(newHabit)

    override suspend fun increaseQuantity(id: Int) =
        habitDao.increaseQuantity(id)

    override suspend fun decreaseQuantity(id: Int) =
        habitDao.decreaseQuantity(id)

    override suspend fun delete(habit: Habit) =
        habitDao.delete(habit)

    override suspend fun deleteById(id: Int) =
        habitDao.deleteById(id)

    override fun getAllHabits(): Flow<List<Habit>> =
        habitDao.getAllHabits()

    override fun getHabit(id: Int): Flow<Habit?> =
        habitDao.getHabit(id)
}