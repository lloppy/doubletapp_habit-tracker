package com.example.domain.repository

import com.example.domain.util.DataError
import com.example.domain.util.EmptyResult
import com.example.domain.util.Result
import com.example.model.domain.Habit
import com.example.model.model.HabitDoneResponse
import com.example.model.model.HabitFetchResponse
import com.example.model.model.HabitUid

interface RemoteDataSource {

    suspend fun getHabits(): Result<List<HabitFetchResponse>, DataError.Network>

    suspend fun updateHabit(habit: Habit): Result<HabitUid, DataError.Network>

    suspend fun deleteHabit(habitUid: HabitUid): EmptyResult<DataError.Network>

    suspend fun markDoneHabit(habitDone: HabitDoneResponse): EmptyResult<DataError.Network>

}