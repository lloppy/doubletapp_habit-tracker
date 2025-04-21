package com.example.data.repository

import android.util.Log
import com.example.data.local.HabitsLocalRepository
import com.example.data.remote.datasource.HabitsRemoteDataSource
import com.example.model.domain.Habit
import com.example.model.model.ApiResult
import com.example.model.model.HabitDoneResponse
import com.example.model.model.HabitFetchResponse
import com.example.model.model.HabitUid
import com.example.model.model.HabitUpdateRequest
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class HabitsRepositoryImpl(
    private val local: HabitsLocalRepository,
    private val remote: HabitsRemoteDataSource,
) {

    suspend fun insertHabit(habit: Habit): Result<Unit> = runCatching {
        when (val result = executeWithRetry {
            remote.updateHabit(HabitUpdateRequest.fromDomain(habit))
        }) {
            is ApiResult.Success -> {
                local.saveHabit(habit.copy(uid = result.data.uid))
            }

            is ApiResult.Error -> throw Exception(result.message)
        }
    }

    suspend fun syncFromRemoteToLocal(): Result<Unit> = runCatching {
        when (val result = executeWithRetry { remote.getHabits() }) {
            is ApiResult.Success -> {
                local.deleteAllHabits()
                result.data.forEach { response ->
                    local.saveHabit(HabitFetchResponse.toDomain(response))
                }
            }

            is ApiResult.Error -> throw Exception(result.message)
        }
    }

    suspend fun syncFromLocalToRemote(): Result<Unit> = runCatching {
        local.getAllHabits().first().forEach { habit ->
            when (val result = executeWithRetry {
                Log.e("SharedHabitsRepository", "date " + habit.date.toString())

                remote.updateHabit(HabitUpdateRequest.fromDomain(habit))
            }) {
                is ApiResult.Success -> Unit
                is ApiResult.Error -> throw Exception("Failed to sync habit ${habit.id}: ${result.message}")
            }
        }
    }

    suspend fun deleteHabit(habit: Habit): Result<Unit> = runCatching {
        when (val result = executeWithRetry {
            remote.deleteHabit(HabitUid(habit.uid))
        }) {
            is ApiResult.Success -> {
                local.deleteHabit(habit)
            }

            is ApiResult.Error -> throw Exception(result.message)
        }
    }

    suspend fun increaseHabitQuantity(id: Int): ApiResult<Unit> {
        return try {
            local.increaseHabitQuantity(id)
            val habit = local.getHabitAtOnce(id)

            if (habit.quantity == habit.repeatedTimes) {
                when (val result = executeWithRetry {
                    remote.markDoneHabit(
                        HabitDoneResponse(
                            date = (System.currentTimeMillis() / 1000).toInt(),
                            habitUid = habit.uid
                        )
                    )
                }) {
                    is ApiResult.Success -> ApiResult.Success(Unit)
                    is ApiResult.Error -> throw Exception(result.message)
                }
            } else {
                ApiResult.Success(Unit)
            }
        } catch (e: Exception) {
            ApiResult.Error(-1, e.message ?: "Failed to increase habit quantity")
        }
    }

    suspend fun deleteByHabitId(id: Int) = local.deleteByHabitId(id)
    suspend fun deleteAllHabits() = local.deleteAllHabits()
    suspend fun decreaseHabitQuantity(id: Int) = local.decreaseHabitQuantity(id)
    suspend fun getHabitAtOnce(id: Int): Habit = local.getHabitAtOnce(id)
    fun getAllHabits(): Flow<List<Habit>> = local.getAllHabits()
    fun getHabitById(id: Int): Flow<Habit?> = local.getHabitById(id)

    suspend fun getHabits(): ApiResult<List<HabitFetchResponse>> = remote.getHabits()
    suspend fun updateHabit(habit: HabitUpdateRequest): ApiResult<HabitUid> =
        remote.updateHabit(habit)

    suspend fun deleteHabit(habitUid: HabitUid): ApiResult<Unit> =
        remote.deleteHabit(habitUid)

    suspend fun markDoneHabit(habitDone: HabitDoneResponse): ApiResult<Unit> =
        remote.markDoneHabit(habitDone)


    private suspend fun <T> executeWithRetry(
        block: suspend () -> ApiResult<T>,
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