package com.example.data.local.datasource

import com.example.data.local.dao.HabitDao
import com.example.model.domain.Habit
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HabitsLocalDataSource @Inject constructor(
    private val habitDao: HabitDao
) {
    suspend fun insertHabit(habit: Habit) {
        habitDao.insert(habit)
    }

    suspend fun updateHabit(habit: Habit) {
        habitDao.update(habit)
    }

    suspend fun deleteHabit(habit: Habit) {
        habitDao.delete(habit)
    }

    suspend fun deleteAllHabits() {
        habitDao.deleteAll()
    }

    fun getHabitById(id: Int): Flow<Habit?> {
        return habitDao.getById(id)
    }

    fun getAllHabits(): Flow<List<Habit>> {
        return habitDao.getAll()
    }

}
