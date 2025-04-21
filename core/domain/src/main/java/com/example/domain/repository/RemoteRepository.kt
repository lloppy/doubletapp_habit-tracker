package com.example.domain.repository

import com.example.model.model.ApiResult
import com.example.model.model.HabitDoneResponse
import com.example.model.model.HabitFetchResponse
import com.example.model.model.HabitUid
import com.example.model.model.HabitUpdateRequest

interface RemoteRepository {

    suspend fun getHabits(): ApiResult<List<HabitFetchResponse>>

    suspend fun updateHabit(habit: HabitUpdateRequest): ApiResult<HabitUid>

    suspend fun deleteHabit(habitUid: HabitUid): ApiResult<Unit>

    suspend fun markDoneHabit(habitDone: HabitDoneResponse): ApiResult<Unit>

}