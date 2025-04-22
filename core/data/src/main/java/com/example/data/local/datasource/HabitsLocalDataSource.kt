package com.example.data.local.datasource

import android.database.sqlite.SQLiteFullException
import com.example.data.local.dao.HabitDao
import com.example.data.local.mappers.toDomain
import com.example.data.local.mappers.toEntity
import com.example.domain.repository.LocalDataSource
import com.example.domain.util.DataError
import com.example.domain.util.EmptyResult
import com.example.domain.util.Result
import com.example.model.Habit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HabitsLocalDataSource @Inject constructor(
    private val dao: HabitDao
) : LocalDataSource {

    override suspend fun insertHabit(habit: Habit): EmptyResult<DataError.Local> {
        return try {
            dao.insert(habitEntity = habit.toEntity())
            Result.Success(Unit)
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun deleteHabit(habit: Habit) {
        dao.delete(habit.toEntity())
    }

    override suspend fun deleteHabitById(id: Int) {
        dao.deleteById(id)
    }

    override suspend fun deleteAllHabits() {
        dao.deleteAll()
    }

    override fun getHabitById(id: Int): Flow<Habit?> {
        return dao.getById(id)
            .map {
                it.toDomain()
            }
    }

    override fun getAllHabits(): Flow<List<Habit>> {
        return dao.getAll()
            .map { entityList ->
                entityList.map {
                    it.toDomain()
                }
            }
    }

}
