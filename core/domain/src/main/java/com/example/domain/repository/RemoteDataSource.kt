package com.example.domain.repository

import com.example.domain.model.HabitDoneResponse
import com.example.domain.model.HabitUid
import com.example.domain.util.DataError
import com.example.domain.util.EmptyResult
import com.example.domain.util.Result
import com.example.model.Habit

interface RemoteDataSource {

    suspend fun getHabits(): Result<List<Habit>, DataError.Network>

    suspend fun updateHabit(habit: Habit): Result<HabitUid, DataError.Network>

    suspend fun deleteHabit(habitUid: String): EmptyResult<DataError.Network>

    suspend fun markDoneHabit(habitDone: HabitDoneResponse): EmptyResult<DataError.Network>

}