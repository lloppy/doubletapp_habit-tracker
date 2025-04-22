package com.example.data.remote.datasource

import com.example.data.remote.api.HabitsApiService
import com.example.data.remote.mapper.toData
import com.example.data.remote.mapper.toDomain
import com.example.domain.model.HabitDoneResponse
import com.example.domain.model.HabitUid
import com.example.domain.repository.RemoteDataSource
import com.example.domain.util.DataError
import com.example.domain.util.EmptyResult
import com.example.domain.util.Result
import com.example.model.Habit

class HabitsRemoteDataSource(
    private val retrofitService: HabitsApiService,
) : RemoteDataSource {

    override suspend fun getHabits(): Result<List<Habit>, DataError.Network> {
        return try {
            val response = retrofitService.getHabits()
                .map { habitResponse ->
                    habitResponse.toDomain()
                }
            Result.Success(response)
        } catch (e: Exception) {
            Result.Error(DataError.Network.UNKNOWN)
        }
    }

    override suspend fun updateHabit(habit: Habit): Result<HabitUid, DataError.Network> {
        return try {
            val response = retrofitService.updateHabit(habit.toData()).toDomain()
            Result.Success(response)
        } catch (e: Exception) {
            Result.Error(DataError.Network.UNKNOWN)
        }
    }

    override suspend fun deleteHabit(habitUid: String): EmptyResult<DataError.Network> {
        return try {
            val response = retrofitService.deleteHabit(habitUid = HabitUid(habitUid).toData())
            Result.Success(response)
        } catch (e: Exception) {
            Result.Error(DataError.Network.UNKNOWN)
        }
    }

    override suspend fun markDoneHabit(habitDone: HabitDoneResponse): EmptyResult<DataError.Network> {
        return try {
            val response = retrofitService.markDoneHabit(habitDone.toData())
            Result.Success(response)
        } catch (e: Exception) {
            Result.Error(DataError.Network.UNKNOWN)
        }
    }

}