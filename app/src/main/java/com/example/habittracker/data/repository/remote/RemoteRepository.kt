package com.example.habittracker.data.repository.remote

import com.example.habittracker.model.model.ApiResult
import com.example.habittracker.model.model.HabitDoneResponse
import com.example.habittracker.model.model.HabitFetchResponse
import com.example.habittracker.model.model.HabitUid
import com.example.habittracker.model.model.HabitUpdateRequest

interface RemoteRepository {
    suspend fun getHabits(): ApiResult<List<HabitFetchResponse>>

    suspend fun updateHabit(habit: HabitUpdateRequest): ApiResult<HabitUid>

    suspend fun deleteHabit(habitUid: HabitUid): ApiResult<Unit>

    suspend fun markDoneHabit(habitDone: HabitDoneResponse): ApiResult<Unit>
}