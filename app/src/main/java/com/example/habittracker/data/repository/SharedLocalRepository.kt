package com.example.habittracker.data.repository

import android.util.Log
import com.example.habittracker.data.repository.local.LocalHabitsRepository
import com.example.habittracker.data.repository.local.LocalRepository
import com.example.habittracker.data.repository.remote.RemoteHabitsRepository
import com.example.habittracker.data.repository.remote.RemoteRepository
import com.example.habittracker.model.domain.Habit
import com.example.habittracker.model.model.ApiResult
import com.example.habittracker.model.model.HabitDoneResponse
import com.example.habittracker.model.model.HabitFetchResponse
import com.example.habittracker.model.model.HabitUid
import com.example.habittracker.model.model.HabitUpdateRequest
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow

interface HabitsRepository : LocalRepository, RemoteRepository {
    suspend fun syncFromRemoteToLocal(): Result<Unit>
    suspend fun syncFromLocalToRemote(): Result<Unit>
}

class SharedHabitsRepository(
    private val local: LocalHabitsRepository,
    private val remote: RemoteHabitsRepository,
) : HabitsRepository {

    override suspend fun insertHabit(habit: Habit): Result<Unit> = runCatching {
        when (val result = executeWithRetry {
            remote.updateHabit(HabitUpdateRequest.fromDomain(habit))
        }) {
            is ApiResult.Success -> {
                local.insertHabit(habit.copy(uid = result.data.uid))
            }

            is ApiResult.Error -> throw Exception(result.message)
        }
    }

    override suspend fun updateHabit(habit: Habit): Result<Unit> = runCatching {
        when (val result = executeWithRetry {
            remote.updateHabit(HabitUpdateRequest.fromDomain(habit))
        }) {
            is ApiResult.Success -> {
                local.updateHabit(habit)
            }

            is ApiResult.Error -> throw Exception(result.message)
        }
    }

    override suspend fun syncFromRemoteToLocal(): Result<Unit> = runCatching {
        when (val result = executeWithRetry { remote.getHabits() }) {
            is ApiResult.Success -> {
                local.deleteAllHabits()
                result.data.forEach { response ->
                    local.insertHabit(HabitFetchResponse.toDomain(response))
                }
            }

            is ApiResult.Error -> throw Exception(result.message)
        }
    }

    override suspend fun syncFromLocalToRemote(): Result<Unit> = runCatching {
        local.getAllHabitsOnce().forEach { habit ->
            when (val result = executeWithRetry {
                remote.updateHabit(HabitUpdateRequest.fromDomain(habit))
            }) {
                is ApiResult.Success -> Unit
                is ApiResult.Error -> throw Exception("Failed to sync habit ${habit.id}: ${result.message}")
            }
        }
    }

    override suspend fun deleteHabit(habit: Habit): Result<Unit> = runCatching {
        when (val result = executeWithRetry {
            remote.deleteHabit(HabitUid(habit.uid))
        }) {
            is ApiResult.Success -> {
                local.deleteHabit(habit)
            }

            is ApiResult.Error -> throw Exception(result.message)
        }
    }

    override suspend fun deleteByHabitId(id: Int) = local.deleteByHabitId(id)
    override suspend fun deleteAllHabits() = local.deleteAllHabits()
    override suspend fun increaseHabitQuantity(id: Int) = local.increaseHabitQuantity(id)
    override suspend fun decreaseHabitQuantity(id: Int) = local.decreaseHabitQuantity(id)
    override suspend fun getAllHabitsOnce(): List<Habit> = local.getAllHabitsOnce()
    override fun getAllHabits(): Flow<List<Habit>> = local.getAllHabits()
    override fun getHabitById(id: Int): Flow<Habit?> = local.getHabitById(id)

    override suspend fun getHabits(): ApiResult<List<HabitFetchResponse>> = remote.getHabits()
    override suspend fun updateHabit(habit: HabitUpdateRequest): ApiResult<HabitUid> =
        remote.updateHabit(habit)

    override suspend fun deleteHabit(habitUid: HabitUid): ApiResult<Unit> =
        remote.deleteHabit(habitUid)

    override suspend fun markDoneHabit(habitDone: HabitDoneResponse): ApiResult<Unit> =
        remote.markDoneHabit(habitDone)


    private suspend fun <T> executeWithRetry(
        block: suspend () -> ApiResult<T>
    ): ApiResult<T> {
        var retryCount = 0
        var lastError: Exception? = null

        while (retryCount < MAX_RETRIES) {
            when (val result = block()) {
                is ApiResult.Success -> return result
                is ApiResult.Error -> {
                    lastError = Exception(result.message)
                    retryCount++
                    if (retryCount < MAX_RETRIES) delay(RETRY_DELAY_MILLIS)
                }
            }
        }
        return ApiResult.Error(-1, lastError?.message ?: "Max retries reached").also {
            Log.e("SharedHabitsRepository", it.message)
        }
    }

    companion object {
        private const val MAX_RETRIES = 3
        private const val RETRY_DELAY_MILLIS = 2000L
    }
}