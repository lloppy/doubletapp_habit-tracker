package com.example.habittracker.data.repository.remote

import com.example.habittracker.data.api.remote.ApiHandler
import com.example.habittracker.data.api.remote.HabitsApiService
import com.example.habittracker.model.model.ApiResult
import com.example.habittracker.model.model.HabitDoneResponse
import com.example.habittracker.model.model.HabitFetchResponse
import com.example.habittracker.model.model.HabitUid
import com.example.habittracker.model.model.HabitUpdateRequest

class RemoteHabitsRepository(
    private val retrofitService: HabitsApiService,
) : RemoteRepository {
    override suspend fun getHabits(): ApiResult<List<HabitFetchResponse>> {
        return ApiHandler.handleApiCall { retrofitService.getHabits() }
    }

    override suspend fun updateHabit(habit: HabitUpdateRequest): ApiResult<HabitUid> {
        return ApiHandler.handleApiCall { retrofitService.updateHabit(habit) }
    }

    override suspend fun deleteHabit(habitUid: HabitUid): ApiResult<Unit> {
        return ApiHandler.handleApiCall { retrofitService.deleteHabit(habitUid) }
    }

    override suspend fun markDoneHabit(habitDone: HabitDoneResponse): ApiResult<Unit> {
        return ApiHandler.handleApiCall { retrofitService.markDoneHabit(habitDone) }
    }
}