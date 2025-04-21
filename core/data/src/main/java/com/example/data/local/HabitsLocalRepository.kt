package com.example.data.local

import com.example.data.local.dao.HabitDao
import com.example.domain.repository.LocalRepository
import com.example.model.domain.Habit
import com.example.model.model.ApiResult
import kotlinx.coroutines.flow.Flow

class HabitsLocalRepository(private val habitDao: HabitDao) : LocalRepository {

    override suspend fun insertHabit(habit: Habit) = runCatching {
        habitDao.insert(habit)
    }

    override suspend fun updateHabit(habit: Habit) = runCatching {
        habitDao.update(habit)
    }

    override suspend fun increaseHabitQuantity(id: Int): ApiResult<Unit> = try {
        habitDao.increaseQuantity(id)
        ApiResult.Success(Unit)
    } catch (e: Exception) {
        ApiResult.Error(-1, e.message ?: "Failed to increase habit quantity")
    }

    override suspend fun decreaseHabitQuantity(id: Int) =
        habitDao.decreaseQuantity(id)

    override suspend fun deleteHabit(habit: Habit): Result<Unit> = runCatching {
        habitDao.delete(habit)
    }

    override suspend fun deleteByHabitId(id: Int) =
        habitDao.deleteById(id)

    override suspend fun deleteAllHabits() {
        habitDao.deleteAll()
    }

    override fun getAllHabits(): Flow<List<Habit>> =
        habitDao.getAll()

    override fun getHabitById(id: Int): Flow<Habit?> =
        habitDao.getById(id)

    override suspend fun getHabitAtOnce(id: Int): Habit = habitDao.getOnce(id)

}