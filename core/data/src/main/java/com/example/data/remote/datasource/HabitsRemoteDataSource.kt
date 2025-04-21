package com.example.data.remote.datasource

import com.example.data.remote.api.HabitsApiService
import com.example.model.model.HabitDoneResponse
import com.example.model.model.HabitFetchResponse
import com.example.model.model.HabitUid
import com.example.model.model.HabitUpdateRequest

class HabitsRemoteDataSource(
    private val retrofitService: HabitsApiService
) {
    suspend fun getHabits(): List<HabitFetchResponse> {
        return retrofitService.getHabits()
    }

    suspend fun updateHabit(habit: HabitUpdateRequest): HabitUid {
        return retrofitService.updateHabit(habit)
    }

    suspend fun deleteHabit(habitUid: HabitUid) {
        return retrofitService.deleteHabit(habitUid)
    }

    suspend fun markDoneHabit(habitDone: HabitDoneResponse) {
        return retrofitService.markDoneHabit(habitDone)
    }
}