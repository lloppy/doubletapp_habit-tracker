package com.example.domain.repository

import com.example.domain.model.HabitDoneResponseModel
import com.example.domain.model.HabitUidModel
import com.example.domain.util.DataError
import com.example.domain.util.EmptyResult
import com.example.domain.util.Result
import com.example.model.Habit

interface RemoteDataSource {

    suspend fun getHabits(): Result<List<Habit>, DataError.Network>

    suspend fun updateHabit(habit: Habit): Result<HabitUidModel, DataError.Network>

    suspend fun deleteHabit(habitUid: String): EmptyResult<DataError.Network>

    suspend fun markDoneHabit(habitDone: HabitDoneResponseModel): EmptyResult<DataError.Network>

}