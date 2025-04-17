package com.example.habittracker.data.repository

import com.example.habittracker.data.repository.local.HabitsRepository
import com.example.habittracker.data.repository.local.OfflineHabitsRepository
import com.example.habittracker.data.repository.remote.OnlineHabitsRepository
import com.example.habittracker.model.domain.Habit
import com.example.habittracker.model.model.ApiResult
import com.example.habittracker.model.model.HabitFetchResponse
import com.example.habittracker.model.model.HabitUid
import com.example.habittracker.model.model.HabitUpdateRequest
import kotlinx.coroutines.flow.Flow

class SharedHabitsRepository(
    private val local: OfflineHabitsRepository,
    private val remote: OnlineHabitsRepository,
) : HabitsRepository {

    override suspend fun updateHabit(habit: Habit): Result<Unit> {
        val habitRequest = HabitUpdateRequest.fromDomain(habit)

        return when (val result = remote.updateHabit(habitRequest)) {
            is ApiResult.Success -> {
                local.updateHabit(habit)
                Result.success(Unit)
            }

            is ApiResult.Error -> Result.failure(Exception(result.message))
        }
    }

    override suspend fun insertHabit(habit: Habit): Result<Unit> {
        val habitRequest = HabitUpdateRequest.fromDomain(habit)

        return when (val result = remote.updateHabit(habitRequest)) {
            is ApiResult.Success -> {
                val habitWithUid = habit.copy(uid = result.data.uid)
                local.insertHabit(habitWithUid)
                Result.success(Unit)
            }

            is ApiResult.Error -> Result.failure(Exception(result.message))
        }
    }

    override suspend fun syncFromRemoteToLocal(): Result<Unit> {
        return when (val remoteResult = remote.getHabits()) {
            is ApiResult.Success -> {
                local.deleteAllHabits()
                remoteResult.data.forEach { response ->
                    local.insertHabit(HabitFetchResponse.toDomain(response))
                }
                Result.success(Unit)
            }

            is ApiResult.Error -> Result.failure(Exception(remoteResult.message))
        }
    }

    override suspend fun syncFromLocalToRemote(): Result<Unit> {
        local.getAllHabitsOnce().forEach { habit ->
            remote.updateHabit(HabitUpdateRequest.fromDomain(habit))
        }
        return Result.success(Unit)
    }

    override suspend fun deleteHabit(habit: Habit): Result<Unit> {
        return when (remote.deleteHabit(HabitUid(habit.uid))) {
            is ApiResult.Success -> {
                local.deleteHabit(habit)
                Result.success(Unit)
            }

            is ApiResult.Error -> Result.failure(Exception("Failed to delete habit on server"))
        }
    }

    override suspend fun deleteByHabitId(id: Int) = local.deleteByHabitId(id)
    override suspend fun deleteAllHabits() = local.deleteAllHabits()
    override suspend fun increaseHabitQuantity(id: Int) = local.increaseHabitQuantity(id)
    override suspend fun decreaseHabitQuantity(id: Int) = local.decreaseHabitQuantity(id)
    override suspend fun getAllHabitsOnce(): List<Habit> = local.getAllHabitsOnce()
    override fun getAllHabits(): Flow<List<Habit>> = local.getAllHabits()
    override fun getHabitById(id: Int): Flow<Habit?> = local.getHabitById(id)
}