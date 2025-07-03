package com.example.data.remote.datasource

import com.example.data.remote.api.HabitsApiService
import com.example.data.remote.mapper.toDto
import com.example.data.remote.mapper.toEntity
import com.example.data.remote.mapper.toModel
import com.example.domain.model.HabitDoneResponseModel
import com.example.domain.model.HabitUidModel
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
                    habitResponse.toEntity()
                }
            Result.Success(response)
        } catch (e: Exception) {
            Result.Error(DataError.Network.UNKNOWN)
        }
    }

    override suspend fun updateHabit(habit: Habit): Result<HabitUidModel, DataError.Network> {
        return try {
            val response = retrofitService.updateHabit(
                habitDto = habit.toDto()
            )
            Result.Success(response.toModel())
        } catch (e: Exception) {
            Result.Error(DataError.Network.UNKNOWN)
        }
    }

    override suspend fun deleteHabit(habitUid: String): EmptyResult<DataError.Network> {
        return try {
            val response = retrofitService.deleteHabit(
                habitUidDto = HabitUidModel(habitUid).toDto()
            )
            Result.Success(response)
        } catch (e: Exception) {
            Result.Error(DataError.Network.UNKNOWN)
        }
    }

    override suspend fun markDoneHabit(habitDone: HabitDoneResponseModel): EmptyResult<DataError.Network> {
        return try {
            val response = retrofitService.markDoneHabit(
                habitDoneDto = habitDone.toDto()
            )
            Result.Success(response)
        } catch (e: Exception) {
            Result.Error(DataError.Network.UNKNOWN)
        }
    }
}